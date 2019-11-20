using Amazon.CDK;
using Amazon.CDK.AWS.EC2;
using Amazon.CDK.AWS.Events;
using Amazon.CDK.AWS.ElasticLoadBalancingV2;

namespace StructFromCallbacks
{
    class Program
    {
        static void Main(string[] args)
        {
            var app = new App(null);
            var stack = new Stack(app, "mystack");
            var rule = new Rule(stack, "myrule", new RuleProps
            {
                Targets=new IRuleTarget[] { new MyEventTarget() },
                Schedule=Schedule.Cron(new CronOptions
                {
                    Day="20"
                })
            });
            var lb = new ApplicationLoadBalancer(stack, "myapplb", new ApplicationLoadBalancerProps
            {
                Vpc=new Vpc(stack, "myvpc")
            });
            var listener = lb.AddListener("Listener", new ApplicationListenerProps
            {
                Port=80
            });
            listener.AddTargets("mylbtarget", new AddApplicationTargetsProps
            {
                Targets=new IApplicationLoadBalancerTarget[] { new MyLBTarget() },
                Port=80
            });
            app.Synth();
        }
    }

    public class MyEventTarget : IRuleTarget
    {
        public IRuleTargetConfig Bind(IRule rule, string id = null)
        {
            return new RuleTargetConfig
            {
                Arn="myruletargetarn",
                EcsParameters=new Amazon.CDK.AWS.Events.CfnRule.EcsParametersProperty
                {
                    TaskDefinitionArn="mytaskdefinitionarn"
                }
            };
        }
    }

    public class MyLBTarget : IApplicationLoadBalancerTarget
    {
        public ILoadBalancerTargetProps AttachToApplicationTargetGroup(IApplicationTargetGroup targetGroup)
        {
            return new LoadBalancerTargetProps
            {
                TargetType=TargetType.IP,
                TargetJson=new MyTargetJson
                {
                    Port=1234,
                    Id="i-1234"
                }
            };
        }
    }

    public class MyTargetJson
    {
        public int Port { get; set; }

        public string Id { get; set; }
    }
}
