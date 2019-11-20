using System;
using Amazon.CDK;
using Events = Amazon.CDK.AWS.Events;
using Amazon.CDK.AWS.EC2;
using Amazon.CDK.AWS.ElasticLoadBalancingV2;

namespace ReturnStructFromCallbacks
{
    public class ReturnStructFromCallbacksStack : Stack
    {
        public class MyEventTarget : Events.IRuleTarget {
          public Events.IRuleTargetConfig Bind(Events.IRule rule, string id) {
            return new Events.RuleTargetConfig() {
                Id = "asdf",
                Arn = "target-arn",
                EcsParameters = new Events.CfnRule.EcsParametersProperty() {
                  TaskDefinitionArn = "task-definition-arn"
                }
            };
          }
        }

        public class MyLbTarget : IApplicationLoadBalancerTarget
        {
            public ILoadBalancerTargetProps AttachToApplicationTargetGroup(IApplicationTargetGroup tg) {
                return new LoadBalancerTargetProps() {
                    TargetType = TargetType.IP,
                    TargetJson = new {
                        port = 1234,
                        id = "i-1234"
                    }
                };
            }
        }

        public class MyListenerProps : IBaseApplicationListenerProps
        {
            public double? Port { get; set; }
        }

        public class MyTargetProps : IAddApplicationTargetsProps
        {
            public double? Port { get; set; }
            public IApplicationLoadBalancerTarget[] Targets { get; set; }
        }

        public ReturnStructFromCallbacksStack(Construct scope, string id, IStackProps props) : base(scope, id, props)
        {
            var rule = new Events.Rule(this, "Rule", new Events.RuleProps() {
                Targets = new Events.IRuleTarget[] {
                    new MyEventTarget()
                }
            });

            var lb = new ApplicationLoadBalancer(this, "LB", new ApplicationLoadBalancerProps() {
                Vpc = new Vpc(this, "VPC")
            });
            var listener = lb.AddListener("Listener", new MyListenerProps() {
                Port = 80
            });
            listener.AddTargets("DefaultTargets", new MyTargetProps() {
                Port = 80,
                Targets = new IApplicationLoadBalancerTarget[] {
                    new MyLbTarget()
                }
            });
        }
    }
}
