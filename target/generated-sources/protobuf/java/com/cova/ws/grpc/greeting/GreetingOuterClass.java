// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: greeting/greeting.proto

package com.cova.ws.grpc.greeting;

public final class GreetingOuterClass {
  private GreetingOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_cova_ws_grpc_greeting_Greeting_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_cova_ws_grpc_greeting_Greeting_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_cova_ws_grpc_greeting_GreetRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_cova_ws_grpc_greeting_GreetRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_cova_ws_grpc_greeting_GreetResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_cova_ws_grpc_greeting_GreetResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_cova_ws_grpc_greeting_GreatManyTimesRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_cova_ws_grpc_greeting_GreatManyTimesRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_cova_ws_grpc_greeting_GreetManyTimesRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_cova_ws_grpc_greeting_GreetManyTimesRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_cova_ws_grpc_greeting_GreetManyTimesResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_cova_ws_grpc_greeting_GreetManyTimesResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\027greeting/greeting.proto\022\031com.cova.ws.g" +
      "rpc.greeting\"1\n\010Greeting\022\022\n\nfirst_name\030\001" +
      " \001(\t\022\021\n\tlast_name\030\002 \001(\t\"E\n\014GreetRequest\022" +
      "5\n\010greeting\030\001 \001(\0132#.com.cova.ws.grpc.gre" +
      "eting.Greeting\"\037\n\rGreetResponse\022\016\n\006resul" +
      "t\030\001 \001(\t\"\'\n\025GreatManyTimesRequest\022\016\n\006resu" +
      "lt\030\001 \001(\t\"N\n\025GreetManyTimesRequest\0225\n\010gre" +
      "eting\030\001 \001(\0132#.com.cova.ws.grpc.greeting." +
      "Greeting\"(\n\026GreetManyTimesResponse\022\016\n\006re" +
      "sult\030\001 \001(\t2\347\001\n\014GreetService\022\\\n\005Greet\022\'.c" +
      "om.cova.ws.grpc.greeting.GreetRequest\032(." +
      "com.cova.ws.grpc.greeting.GreetResponse\"" +
      "\000\022y\n\016GreetManyTimes\0220.com.cova.ws.grpc.g" +
      "reeting.GreetManyTimesRequest\0321.com.cova" +
      ".ws.grpc.greeting.GreetManyTimesResponse" +
      "\"\0000\001B\035\n\031com.cova.ws.grpc.greetingP\001b\006pro" +
      "to3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_com_cova_ws_grpc_greeting_Greeting_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_cova_ws_grpc_greeting_Greeting_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_cova_ws_grpc_greeting_Greeting_descriptor,
        new java.lang.String[] { "FirstName", "LastName", });
    internal_static_com_cova_ws_grpc_greeting_GreetRequest_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_com_cova_ws_grpc_greeting_GreetRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_cova_ws_grpc_greeting_GreetRequest_descriptor,
        new java.lang.String[] { "Greeting", });
    internal_static_com_cova_ws_grpc_greeting_GreetResponse_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_com_cova_ws_grpc_greeting_GreetResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_cova_ws_grpc_greeting_GreetResponse_descriptor,
        new java.lang.String[] { "Result", });
    internal_static_com_cova_ws_grpc_greeting_GreatManyTimesRequest_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_com_cova_ws_grpc_greeting_GreatManyTimesRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_cova_ws_grpc_greeting_GreatManyTimesRequest_descriptor,
        new java.lang.String[] { "Result", });
    internal_static_com_cova_ws_grpc_greeting_GreetManyTimesRequest_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_com_cova_ws_grpc_greeting_GreetManyTimesRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_cova_ws_grpc_greeting_GreetManyTimesRequest_descriptor,
        new java.lang.String[] { "Greeting", });
    internal_static_com_cova_ws_grpc_greeting_GreetManyTimesResponse_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_com_cova_ws_grpc_greeting_GreetManyTimesResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_cova_ws_grpc_greeting_GreetManyTimesResponse_descriptor,
        new java.lang.String[] { "Result", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
