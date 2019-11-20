using Amazon.CDK;
using Amazon.CDK.AWS.SAM;
using System;
using System.Collections.Generic;
using System.Diagnostics;

namespace Union
{
    class Program
    {
        static void Main(string[] args)
        {
            var app = new App();
            var stack = new Stack(app, "mystack");
            var events = new Dictionary<string, object>();
            events.Add("Event", new CfnFunction.EventSourceProperty()
            {
                Type="type1",
                Properties=new CfnFunction.SQSEventProperty()
                {
                    Queue="mysqsqueue"
                }
            });
            var fn = new CfnFunction(stack, "myfunction", new CfnFunctionProps
            {
                CodeUri="mycodeuri",
                Handler="index.handler",
                Runtime="nodejs10.x",
                Events=events
            });
            Debug.Assert(
                ((CfnFunction.SQSEventProperty)
                    ((CfnFunction.EventSourceProperty)
                        ((Dictionary<string, object>)fn.Events)["Event"]
                    ).Properties
                ).Queue == "mysqsqueue");
            app.Synth();
        }
    }
}
