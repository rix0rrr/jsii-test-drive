using Amazon.CDK;
using Amazon.CDK.AWS.Lambda;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Serverless
{
    public class MyAppFunction : FunctionBase
    {
        public MyAppFunction(Construct scope, string id) : base(scope, id)
        {
        }
    }

    public class Node12Aspect : IAspect
    {
        public void Visit(IConstruct construct)
        {
            if (construct.GetType().Name.CompareTo("CfnFunction") == 0)
            {
                ((CfnResource) construct).AddPropertyOverride("Runtime", "nodejs12.x");
            }
        }
    }


    class Program
    {
        static void Main(string[] args)
        {
            var app = new App(null);
            var stack = new Stack(app, "mystack");
            var fn = new Function(stack, "myfunction", new FunctionProps {
                Code = Code.FromInline("my code here"),
                Handler = "index.handler",
                Runtime = Runtime.NODEJS_8_10
            });
            CfnFunction cfnfn = (CfnFunction) fn.Node.DefaultChild;
            cfnfn.AddPropertyOverride("Runtime", "nodejs10.x");

            var version = new CfnVersion(stack, $"{fn.Node.Id}-Version", new CfnVersionProps {
                FunctionName = fn.FunctionName
            });
            version.Node.AddDependency(cfnfn);

            var myappfn = new MyAppFunction(stack, "myappfunction");
            // Console.WriteLine(myappfn.FunctionName);

            stack.Node.ApplyAspect(new Node12Aspect());

            app.Synth();
        }
    }
}
