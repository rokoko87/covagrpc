// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: greet/greet.proto

package com.cova.ws.grpc.greet;

public final class Greet {
  private Greet() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_cova_ws_grpc_greet_HelloRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_cova_ws_grpc_greet_HelloRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_cova_ws_grpc_greet_HelloReply_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_cova_ws_grpc_greet_HelloReply_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\021greet/greet.proto\022\026com.cova.ws.grpc.gr" +
      "eet\"\034\n\014HelloRequest\022\014\n\004name\030\001 \001(\t\"\033\n\nHel" +
      "loReply\022\r\n\005reply\030\001 \001(\t2a\n\007Greeter\022V\n\010Say" +
      "Hello\022$.com.cova.ws.grpc.greet.HelloRequ" +
      "est\032\".com.cova.ws.grpc.greet.HelloReply\"" +
      "\000B\032\n\026com.cova.ws.grpc.greetP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_com_cova_ws_grpc_greet_HelloRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_cova_ws_grpc_greet_HelloRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_cova_ws_grpc_greet_HelloRequest_descriptor,
        new java.lang.String[] { "Name", });
    internal_static_com_cova_ws_grpc_greet_HelloReply_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_com_cova_ws_grpc_greet_HelloReply_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_cova_ws_grpc_greet_HelloReply_descriptor,
        new java.lang.String[] { "Reply", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
