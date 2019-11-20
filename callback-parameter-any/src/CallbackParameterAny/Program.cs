using Amazon.CDK;
using Amazon.CDK.AWS.Elasticsearch;
using System;
using System.Collections.Generic;
using System.Linq;

namespace CallbackParameterAny
{
    class Program
    {
        static void Main(string[] args)
        {
            var app = new App();
            var stack = new Stack(app, "mystack");

            var statement = new Dictionary<string, object>();
            statement.Add("Effect", "Allow");
            statement.Add("Principal", new Dictionary<string, string>()
            {
                { "AWS", "user-arn" }
            });
            statement.Add("Action", "*");

            new CfnDomain(stack, "myelasticsearchdomain", new CfnDomainProps
            {
                AccessPolicies=new Dictionary<string, object>()
                {
                    { "Version", "2012-10-17" },
                    { "Statements", new Dictionary<string, object>[] { statement } }
                }
            });
            app.Synth();
        }
    }
}
