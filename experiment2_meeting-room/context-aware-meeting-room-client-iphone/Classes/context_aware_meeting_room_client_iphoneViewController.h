//
//  context_aware_meeting_room_client_iphoneViewController.h
//  context-aware-meeting-room-client-iphone
//
//  Created by Jarle Hansen on 3/21/10.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "NoteProtobuf.pb.h"

@interface context_aware_meeting_room_client_iphoneViewController : UIViewController <UIActionSheetDelegate> {
	UIButton *nextButton;
	UIButton *prevButton;
	UITextView *noteMessage;
	NSOperationQueue *queue;
}

@property (nonatomic, retain) NSOperationQueue *queue;
@property (nonatomic, retain) IBOutlet UIButton *nextButton;
@property (nonatomic, retain) IBOutlet UIButton *prevButton;
@property (nonatomic, retain) IBOutlet UITextView *noteMessage;

- (void)backgroundWork;
- (void)updateMessageNoteText:(NSString *)lastNoteMessage;

- (IBAction)nextButtonPushed:(id)sender;
- (IBAction)prevButtonPushed:(id)sender;

@end

