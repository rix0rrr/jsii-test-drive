package com.myorg;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.IAnyProducer;
import software.amazon.awscdk.core.IResolveContext;
import software.amazon.awscdk.core.Lazy;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.services.ec2.Connections;
import software.amazon.awscdk.services.ec2.IPeer;
import software.amazon.awscdk.services.ec2.IVpc;
import software.amazon.awscdk.services.ec2.Port;
import software.amazon.awscdk.services.ec2.SecurityGroup;
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.ec2.VpcAttributes;

import java.util.HashMap;

import static java.util.Arrays.asList;

public class HelloStack extends Stack {
    public HelloStack(final Construct parent, final String id) {
        this(parent, id, null);
    }

    public HelloStack(final Construct parent, final String id, final StackProps props) {
        super(parent, id, props);

        IVpc vpc = Vpc.fromVpcAttributes(this, "VPC", VpcAttributes.builder()
                .vpcId("vpc-987")
                .availabilityZones(asList("us-west-1a"))
                .publicSubnetIds(asList("pub-subnet-1"))
                .build());

        SecurityGroup sg = SecurityGroup.Builder.create(this, "SG")
                .vpc(vpc)
                .build();
        sg.getConnections().allowFrom(new SomePeer(), Port.tcp(1));

        // this line breaks it
        sg.getConnections().allowFrom(new RoundaboutPeer(), Port.tcp(1));
    }
}

class SomePeer implements IPeer {
    private final Connections connections = Connections.Builder.create()
            .peer(this)
            .build();

    @Override
    public Connections getConnections() {
        return this.connections;
    }

    @Override
    public Boolean getCanInlineRule() {
        return true;
    }

    @Override
    public String getUniqueId() {
        return getClass().getCanonicalName();
    }

    @Override
    public Object toEgressRuleConfig() {
        return new HashMap<String, Object>() {{
                put("cidrIp", "1.2.3.4/5");
                put("key1", "val1");
        }};
    }

    @Override
    public Object toIngressRuleConfig() {
        return new HashMap<String, Object>() {{
            put("cidrIp", "2.3.4.5/6");
            put("key2", "val2");
        }};
    }
}

class RoundaboutPeer implements IPeer {
    private final Connections connections = Connections.Builder.create()
            .peer(this)
            .build();

    @Override
    public Connections getConnections() {
        return this.connections;
    }

    @Override
    public Boolean getCanInlineRule() {
        return true;
    }

    @Override
    public String getUniqueId() {
        return getClass().getCanonicalName();
    }

    @Override
    public Object toEgressRuleConfig() {
        return new HashMap<String, Object>() {{
            put("cidrIp", Lazy.anyValue(new RoundaboutValue("3.4.5.6/7")));
            put("key3", "val3");
        }};
    }

    @Override
    public Object toIngressRuleConfig() {
        return new HashMap<String, Object>() {{
            put("cidrIp", Lazy.anyValue(new RoundaboutValue("4.5.6.7/8")));
            put("key4", "val4");
        }};
    }
}

class RoundaboutValue implements IAnyProducer {
    private final String value;

    public RoundaboutValue(String value) {
        this.value = value;
    }

    @Override
    public Object produce(IResolveContext context) {
        return value;
    }
}
