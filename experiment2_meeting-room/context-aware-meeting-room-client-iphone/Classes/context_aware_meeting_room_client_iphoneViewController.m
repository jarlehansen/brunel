//
//  context_aware_meeting_room_client_iphoneViewController.m
//  context-aware-meeting-room-client-iphone
//
//  Created by Jarle Hansen on 3/21/10.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import "context_aware_meeting_room_client_iphoneViewController.h"
#import "NoteProtobuf.pb.h"
#import <CFNetwork/CFNetwork.h>
#import "ASIHTTPRequest.h"

@implementation context_aware_meeting_room_client_iphoneViewController


@synthesize nextButton;
@synthesize prevButton;
@synthesize noteMessage;
@synthesize queue;

int counter;
int historyCounter;
NSMutableArray *notes;

/*
// The designated initializer. Override to perform setup that is required before the view is loaded.
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
        // Custom initialization
    }
    return self;
}
*/

/*
// Implement loadView to create a view hierarchy programmatically, without using a nib.
- (void)loadView {
}
*/



// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {	
	UIImage *buttonImageNormal = [UIImage imageNamed:@"whiteButton.png"];
	UIImage *stretchableButtonImageNormal = [buttonImageNormal stretchableImageWithLeftCapWidth:12 topCapHeight:0];
	[nextButton setBackgroundImage:stretchableButtonImageNormal forState:UIControlStateNormal];
	[prevButton setBackgroundImage:stretchableButtonImageNormal forState:UIControlStateNormal];
	
	UIImage *buttonPressed = [UIImage imageNamed:@"blueButton.png"];
	UIImage *stretchableButtonImagePressed = [buttonPressed stretchableImageWithLeftCapWidth:12 topCapHeight:0];
	[nextButton setBackgroundImage:stretchableButtonImagePressed forState:UIControlStateHighlighted];
	[prevButton setBackgroundImage:stretchableButtonImagePressed forState:UIControlStateHighlighted];
	
	
	UIActionSheet *actionSheet = [[UIActionSheet alloc] initWithTitle:@"Participant" delegate:self
													cancelButtonTitle:@"Exit" destructiveButtonTitle:@"Presentation" otherButtonTitles:nil];
	[actionSheet showInView:self.view];
	[actionSheet release];
	
    [super viewDidLoad];
}

- (void)actionSheet:(UIActionSheet *)actionSheet didDismissWithButtonIndex:(NSInteger)buttonIndex {
	if(buttonIndex == [actionSheet cancelButtonIndex]) {
		exit(0);
	} else {
		NSLog(@"Starting presentation mode");
		
		[self setQueue:[[[NSOperationQueue alloc] init] autorelease]];
		
		queue = [[NSOperationQueue alloc] init];
		NSInvocationOperation *operation = [[NSInvocationOperation alloc] initWithTarget:self selector:@selector(backgroundWork) object:nil];
		[queue addOperation:operation];
		[operation release];
	}
}

-(void)backgroundWork{
	[NSThread sleepForTimeInterval:1];
	
	counter = 0;
	historyCounter = 0;
	notes = [[NSMutableArray alloc] initWithCapacity:10];
	
	NSString *lastNoteMessage = @"";
	
	NSLog(@"Starting background thread");
	
	while(true){
		Note *requestNote = [[[Note builder] setBtAddress:@"012345678900"]build];
		NSURL *url = [NSURL URLWithString:@"http://context-aware-meeting-room.appspot.com/presentationNotes"];
		ASIHTTPRequest *request = [ASIHTTPRequest requestWithURL:url];
		[request addRequestHeader:@"User-Agent" value:@"iPhone"];
		[request appendPostData:[requestNote data]];
		[request setRequestMethod:@"POST"];
		
		[request startSynchronous];
		NSError *error = [request error];
		
		if(error) {
			NSLog(@"Error in network communication %@", [error userInfo]);
		} else {
			NSData *data = [request responseData];
			Note *receivedNote = [Note parseFromData:data];
			NSString *tempMessage = [receivedNote message];
			
			if(![lastNoteMessage isEqualToString:tempMessage]) {
				lastNoteMessage = tempMessage;
				[notes addObject:lastNoteMessage];
				counter++;
				historyCounter = (counter - 1);
				
				NSLog(@"Received note: %@", lastNoteMessage);
			
				[self performSelectorOnMainThread:@selector(updateMessageNoteText:) withObject:lastNoteMessage waitUntilDone:YES];
			} else {
				NSLog(@"Same note message received");
			}
		}
		
		[NSThread sleepForTimeInterval:1];
	}
	
	[notes release];
}

- (void)updateMessageNoteText:(NSString *)lastNoteMessage {
	self.noteMessage.text = lastNoteMessage;
}

- (IBAction)nextButtonPushed:(id)sender {
	NSLog(@"Next button pushed");
	
	if(counter > 0) {
		if(historyCounter < (counter - 1)) {
			historyCounter++;
		}
		
		NSString *tempNote = [notes objectAtIndex:historyCounter];
		NSLog(@"next note: %@", tempNote);
		[self updateMessageNoteText:tempNote];
	}
}

- (IBAction)prevButtonPushed:(id)sender {
	NSLog(@"Prev button pushed");
	
	if(counter > 0) {
		if(historyCounter > 0) {
			historyCounter--;
		}
		
		NSString *tempNote = [notes objectAtIndex:historyCounter];
		NSLog(@"prev note: %@", tempNote);
		[self updateMessageNoteText:tempNote];
	}
}

/*
// Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}
*/

- (void)didReceiveMemoryWarning {
	// Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
	
	// Release any cached data, images, etc that aren't in use.
}



- (void)viewDidUnload {
	// Release any retained subviews of the main view.
	// e.g. self.myOutlet = nil;
}


- (void)dealloc {
	[nextButton release];
	[prevButton release];
	[noteMessage release];
	[notes release];
	
    [super dealloc];
}

@end
