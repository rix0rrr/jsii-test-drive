using Amazon.CDK;
using Amazon.CDK.AWS.EC2;
using Amazon.JSII.Runtime.Deputy;
using System;
using System.Collections.Generic;
using System.Linq;

namespace CallbackAny
{
    class Program
    {
        static void Main(string[] args)
        {
            var app = new App();
            var stack = new Stack(app, "mystack");
            var vpc = new Vpc(stack, "myvpc");
            var sg = new SecurityGroup(stack, "mysg", new SecurityGroupProps
            {
                Vpc=vpc
            });
            sg.Connections.AllowFrom(new SomeIP(), Port.Tcp(1));
            sg.Connections.AllowFrom(new RoundaboutIP(), Port.Tcp(1));
            app.Synth();
        }
    }

    class SomeIP : DeputyBase, IPeer
    {
        public SomeIP()
        {
            Connections = new Connections_(new ConnectionsProps
            {
                Peer=this
            });
            CanInlineRule = true;
            UniqueId = "uniqueid";
        }

        public bool CanInlineRule { get; }

        public string UniqueId { get; }

        public Connections_ Connections { get; }

        public object ToIngressRuleConfig()
        {
            var ingressRule = new Dictionary<string, object>();
            ingressRule.Add("cidrIp", "1.2.3.4/5");
            ingressRule.Add("somethingElse", "bye");
            return ingressRule;
        }

        public object ToEgressRuleConfig()
        {
            var egressRule = new Dictionary<string, string>();
            return egressRule;
        }
    }

    class RoundaboutIP : DeputyBase, IPeer
    {
        public RoundaboutIP()
        {
            Connections = new Connections_(new ConnectionsProps
            {
                Peer=this
            });
            CanInlineRule = true;
            UniqueId = "uniqueid-2";
        }

        public bool CanInlineRule { get; }

        public string UniqueId { get; }

        public Connections_ Connections { get; }

        public object ToIngressRuleConfig()
        {
            var ingressRule = new Dictionary<string, object>();
            ingressRule.Add("cidrIp", Lazy.AnyValue(new Roundabout("1.2.3.5/6")));
            return ingressRule;
        }

        public object ToEgressRuleConfig()
        {
            var egressRule = new Dictionary<string, string>();
            return egressRule;
        }
    }

    public class Roundabout : DeputyBase, IAnyProducer
    {
        private string value;

        public Roundabout(string value)
        {
            this.value = value;
        }

        public object Produce(IResolveContext context)
        {
            return this.value;
        }
    }
}
