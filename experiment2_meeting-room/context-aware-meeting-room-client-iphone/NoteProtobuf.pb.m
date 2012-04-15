// Generated by the protocol buffer compiler.  DO NOT EDIT!

#import "NoteProtobuf.pb.h"

@implementation NoteProtobufRoot
static PBExtensionRegistry* extensionRegistry = nil;
+ (PBExtensionRegistry*) extensionRegistry {
  return extensionRegistry;
}

+ (void) initialize {
  if (self == [NoteProtobufRoot class]) {
    PBMutableExtensionRegistry* registry = [PBMutableExtensionRegistry registry];
    [self registerAllExtensions:registry];
    extensionRegistry = [registry retain];
  }
}
+ (void) registerAllExtensions:(PBMutableExtensionRegistry*) registry {
}
@end

@interface Note ()
@property (retain) NSString* btAddress;
@property (retain) NSString* meetingId;
@property (retain) NSString* message;
@end

@implementation Note

- (BOOL) hasBtAddress {
  return !!hasBtAddress_;
}
- (void) setHasBtAddress:(BOOL) value {
  hasBtAddress_ = !!value;
}
@synthesize btAddress;
- (BOOL) hasMeetingId {
  return !!hasMeetingId_;
}
- (void) setHasMeetingId:(BOOL) value {
  hasMeetingId_ = !!value;
}
@synthesize meetingId;
- (BOOL) hasMessage {
  return !!hasMessage_;
}
- (void) setHasMessage:(BOOL) value {
  hasMessage_ = !!value;
}
@synthesize message;
- (void) dealloc {
  self.btAddress = nil;
  self.meetingId = nil;
  self.message = nil;
  [super dealloc];
}
- (id) init {
  if ((self = [super init])) {
    self.btAddress = @"";
    self.meetingId = @"";
    self.message = @"";
  }
  return self;
}
static Note* defaultNoteInstance = nil;
+ (void) initialize {
  if (self == [Note class]) {
    defaultNoteInstance = [[Note alloc] init];
  }
}
+ (Note*) defaultInstance {
  return defaultNoteInstance;
}
- (Note*) defaultInstance {
  return defaultNoteInstance;
}
- (BOOL) isInitialized {
  if (!self.hasBtAddress) {
    return NO;
  }
  return YES;
}
- (void) writeToCodedOutputStream:(PBCodedOutputStream*) output {
  if (self.hasBtAddress) {
    [output writeString:1 value:self.btAddress];
  }
  if (self.hasMeetingId) {
    [output writeString:2 value:self.meetingId];
  }
  if (self.hasMessage) {
    [output writeString:3 value:self.message];
  }
  [self.unknownFields writeToCodedOutputStream:output];
}
- (int32_t) serializedSize {
  int32_t size = memoizedSerializedSize;
  if (size != -1) {
    return size;
  }

  size = 0;
  if (self.hasBtAddress) {
    size += computeStringSize(1, self.btAddress);
  }
  if (self.hasMeetingId) {
    size += computeStringSize(2, self.meetingId);
  }
  if (self.hasMessage) {
    size += computeStringSize(3, self.message);
  }
  size += self.unknownFields.serializedSize;
  memoizedSerializedSize = size;
  return size;
}
+ (Note*) parseFromData:(NSData*) data {
  return (Note*)[[[Note builder] mergeFromData:data] build];
}
+ (Note*) parseFromData:(NSData*) data extensionRegistry:(PBExtensionRegistry*) extensionRegistry {
  return (Note*)[[[Note builder] mergeFromData:data extensionRegistry:extensionRegistry] build];
}
+ (Note*) parseFromInputStream:(NSInputStream*) input {
  return (Note*)[[[Note builder] mergeFromInputStream:input] build];
}
+ (Note*) parseFromInputStream:(NSInputStream*) input extensionRegistry:(PBExtensionRegistry*) extensionRegistry {
  return (Note*)[[[Note builder] mergeFromInputStream:input extensionRegistry:extensionRegistry] build];
}
+ (Note*) parseFromCodedInputStream:(PBCodedInputStream*) input {
  return (Note*)[[[Note builder] mergeFromCodedInputStream:input] build];
}
+ (Note*) parseFromCodedInputStream:(PBCodedInputStream*) input extensionRegistry:(PBExtensionRegistry*) extensionRegistry {
  return (Note*)[[[Note builder] mergeFromCodedInputStream:input extensionRegistry:extensionRegistry] build];
}
+ (Note_Builder*) builder {
  return [[[Note_Builder alloc] init] autorelease];
}
+ (Note_Builder*) builderWithPrototype:(Note*) prototype {
  return [[Note builder] mergeFrom:prototype];
}
- (Note_Builder*) builder {
  return [Note builder];
}
@end

@interface Note_Builder()
@property (retain) Note* result;
@end

@implementation Note_Builder
@synthesize result;
- (void) dealloc {
  self.result = nil;
  [super dealloc];
}
- (id) init {
  if ((self = [super init])) {
    self.result = [[[Note alloc] init] autorelease];
  }
  return self;
}
- (PBGeneratedMessage*) internalGetResult {
  return result;
}
- (Note_Builder*) clear {
  self.result = [[[Note alloc] init] autorelease];
  return self;
}
- (Note_Builder*) clone {
  return [Note builderWithPrototype:result];
}
- (Note*) defaultInstance {
  return [Note defaultInstance];
}
- (Note*) build {
  [self checkInitialized];
  return [self buildPartial];
}
- (Note*) buildPartial {
  Note* returnMe = [[result retain] autorelease];
  self.result = nil;
  return returnMe;
}
- (Note_Builder*) mergeFrom:(Note*) other {
  if (other == [Note defaultInstance]) {
    return self;
  }
  if (other.hasBtAddress) {
    [self setBtAddress:other.btAddress];
  }
  if (other.hasMeetingId) {
    [self setMeetingId:other.meetingId];
  }
  if (other.hasMessage) {
    [self setMessage:other.message];
  }
  [self mergeUnknownFields:other.unknownFields];
  return self;
}
- (Note_Builder*) mergeFromCodedInputStream:(PBCodedInputStream*) input {
  return [self mergeFromCodedInputStream:input extensionRegistry:[PBExtensionRegistry emptyRegistry]];
}
- (Note_Builder*) mergeFromCodedInputStream:(PBCodedInputStream*) input extensionRegistry:(PBExtensionRegistry*) extensionRegistry {
  PBUnknownFieldSet_Builder* unknownFields = [PBUnknownFieldSet builderWithUnknownFields:self.unknownFields];
  while (YES) {
    int32_t tag = [input readTag];
    switch (tag) {
      case 0:
        [self setUnknownFields:[unknownFields build]];
        return self;
      default: {
        if (![self parseUnknownField:input unknownFields:unknownFields extensionRegistry:extensionRegistry tag:tag]) {
          [self setUnknownFields:[unknownFields build]];
          return self;
        }
        break;
      }
      case 10: {
        [self setBtAddress:[input readString]];
        break;
      }
      case 18: {
        [self setMeetingId:[input readString]];
        break;
      }
      case 26: {
        [self setMessage:[input readString]];
        break;
      }
    }
  }
}
- (BOOL) hasBtAddress {
  return result.hasBtAddress;
}
- (NSString*) btAddress {
  return result.btAddress;
}
- (Note_Builder*) setBtAddress:(NSString*) value {
  result.hasBtAddress = YES;
  result.btAddress = value;
  return self;
}
- (Note_Builder*) clearBtAddress {
  result.hasBtAddress = NO;
  result.btAddress = @"";
  return self;
}
- (BOOL) hasMeetingId {
  return result.hasMeetingId;
}
- (NSString*) meetingId {
  return result.meetingId;
}
- (Note_Builder*) setMeetingId:(NSString*) value {
  result.hasMeetingId = YES;
  result.meetingId = value;
  return self;
}
- (Note_Builder*) clearMeetingId {
  result.hasMeetingId = NO;
  result.meetingId = @"";
  return self;
}
- (BOOL) hasMessage {
  return result.hasMessage;
}
- (NSString*) message {
  return result.message;
}
- (Note_Builder*) setMessage:(NSString*) value {
  result.hasMessage = YES;
  result.message = value;
  return self;
}
- (Note_Builder*) clearMessage {
  result.hasMessage = NO;
  result.message = @"";
  return self;
}
@end

