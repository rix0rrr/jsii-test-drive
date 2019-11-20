using Amazon.CDK;
using System;
using System.Collections.Generic;
using System.Linq;

namespace ReturnStructFromCallbacks
{
    class Program
    {
        static void Main(string[] args)
        {
            var app = new App();
            new ReturnStructFromCallbacksStack(app, "ReturnStructFromCallbacksStack", new StackProps());
            app.Synth();
        }
    }
}
