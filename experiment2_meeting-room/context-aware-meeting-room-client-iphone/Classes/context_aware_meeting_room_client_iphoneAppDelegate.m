//
//  context_aware_meeting_room_client_iphoneAppDelegate.m
//  context-aware-meeting-room-client-iphone
//
//  Created by Jarle Hansen on 3/21/10.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import "context_aware_meeting_room_client_iphoneAppDelegate.h"
#import "context_aware_meeting_room_client_iphoneViewController.h"

@implementation context_aware_meeting_room_client_iphoneAppDelegate

@synthesize window;
@synthesize viewController;


- (void)applicationDidFinishLaunching:(UIApplication *)application {    
    
    // Override point for customization after app launch    
    [window addSubview:viewController.view];
    [window makeKeyAndVisible];
}


- (void)dealloc {
    [viewController release];
    [window release];
    [super dealloc];
}


@end
