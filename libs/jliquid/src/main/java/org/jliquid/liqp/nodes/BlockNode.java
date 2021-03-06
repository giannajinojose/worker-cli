package org.jliquid.liqp.nodes;

import org.jliquid.liqp.LimitedStringBuilder;
import org.jliquid.liqp.tags.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BlockNode implements LNode {

    private List<LNode> children;

    public BlockNode() {
        children = new ArrayList<LNode>();
    }

    public void add(LNode node) {
        children.add(node);
    }

    public List<LNode> getChildren() {
        return new ArrayList<LNode>(children);
    }

    @Override
    public Object render(Map<String, Object> context) {

        LimitedStringBuilder builder = new LimitedStringBuilder();

        for (LNode node : children) {

            Object value = node.render(context);

            if (value == null) {
                continue;
            }

            if (value == Tag.Statement.BREAK || value == Tag.Statement.CONTINUE) {
                return value;
            } else if (value.getClass().isArray()) {

                Object[] array = (Object[]) value;

                for (Object obj : array) {
                    builder.append(String.valueOf(obj));
                }
            } else {
                builder.append(String.valueOf(value));
            }
        }

        return builder.toString();
    }
}
