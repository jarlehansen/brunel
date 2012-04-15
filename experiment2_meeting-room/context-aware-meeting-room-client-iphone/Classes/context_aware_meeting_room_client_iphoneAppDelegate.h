//
//  context_aware_meeting_room_client_iphoneAppDelegate.h
//  context-aware-meeting-room-client-iphone
//
//  Created by Jarle Hansen on 3/21/10.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import <UIKit/UIKit.h>

@class context_aware_meeting_room_client_iphoneViewController;

@interface context_aware_meeting_room_client_iphoneAppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
    context_aware_meeting_room_client_iphoneViewController *viewController;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet context_aware_meeting_room_client_iphoneViewController *viewController;

@end

