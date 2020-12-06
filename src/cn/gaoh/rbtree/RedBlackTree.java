package cn.gaoh.rbtree;


/**
 * @Description:
 * @Author: gaoh
 * @Date: 2020/11/30 19:59
 * @Version: 1.0
 */
public class RedBlackTree<K extends Comparable<K>, V> {


    private RBNode<K, V> root;//根节点

    public RBNode<K, V> getRoot() {
        return root;
    }

    public void setRoot(RBNode<K, V> root) {
        this.root = root;
    }

    static class RBNode<K, V> {
        private RBNode<K, V> left;//左节点
        private RBNode<K, V> right;//右节点
        private RBNode<K, V> parent;//父节点
        private boolean red = true;//颜色  默认为红色
        private K k;//key
        private V v;//value

        public RBNode<K, V> getLeft() {
            return left;
        }

        public void setLeft(RBNode<K, V> left) {
            this.left = left;
        }

        public RBNode<K, V> getRight() {
            return right;
        }

        public void setRight(RBNode<K, V> right) {
            this.right = right;
        }

        public RBNode<K, V> getParent() {
            return parent;
        }

        public void setParent(RBNode<K, V> parent) {
            this.parent = parent;
        }

        public boolean isRed() {
            return red;
        }

        public void setRed(boolean red) {
            this.red = red;
        }

        public K getK() {
            return k;
        }

        public void setK(K k) {
            this.k = k;
        }

        public V getV() {
            return v;
        }

        public void setV(V v) {
            this.v = v;
        }

        public RBNode(K k, V v) {
            this.k = k;
            this.v = v == null ? (V) k : v;
        }

        public RBNode() {
        }
    }

    /**
     * 右旋
     * <p>
     * pp                   pp
     * /                     /
     * p                     pl
     * /  \                  /   \
     * pl   pr              ppl     p
     * / \                          / \
     * pll plr                      plr  pr
     *
     * @param p
     */
    private void rotateRight(RBNode<K, V> p) {
        if (p != null) {
            RBNode<K, V> pl = p.left;
            p.left = pl.right;
            //判断pl的右节点是否存在
            if (pl.right != null) {//将plr变成p的左节点
                pl.right.parent = p;
            }
            pl.parent = p.parent;
            if (p.parent != null) {
                //p是父亲的左节点
                if (p == p.parent.left) {
                    p.parent.left = pl;
                } else {
                    p.parent.right = pl;
                }
            } else {
                root = pl;
            }
            //将pl换成p的父节点
            pl.right = p;
            p.parent = pl;
        }
    }

    /**
     * 左旋
     * pp                       pp
     * /                        /
     * p                        pr
     * /  \                     /  \
     * pl   pr                   p    plr
     * / \                      / \
     * pll plr                  pl  pll
     *
     * @param p
     */
    private void rotateLeft(RBNode<K, V> p) {
        if (p != null) {
            RBNode<K, V> pr = p.right;
            p.right = pr.left;
            if (pr.left != null) {
                pr.left.parent = p;
            }
            pr.parent = p.parent;

            RBNode<K, V> pp;
            if ((pp = p.parent) != null) {
                if (p == pp.left) {
                    pp.left = pr;
                } else {
                    pp.right = pr;
                }
            } else {
                root = pr;
            }
            p.parent = pr;
            pr.left = p;
        }

    }


    /**
     * 插入节点
     *
     * @param kay
     * @param val
     * @return
     */
    public RBNode<K, V> put(K kay, V val) {
        if (kay == null) {
            return null;
        }
        RBNode<K, V> _root = this.root;
        if (_root == null) {
            this.root = new RBNode<K, V>(kay, val);
            this.root.red = false;
            return null;
        }
        RBNode<K, V> parent;
        do {
            parent = _root;
            if (kay.compareTo(_root.k) < 0) {
                _root = _root.left;
            } else if (kay.compareTo(_root.k) > 0) {
                _root = _root.right;
            } else {
                _root.v = val;
                return _root;
            }
        } while (_root != null);

        RBNode<K, V> child = new RBNode<>(kay, val);
        child.parent = parent;
        if (kay.compareTo(parent.k) < 0) {
            parent.left = child;
        } else {
            parent.right = child;
        }

        balanceInsertion(child);

        return null;
    }


    /**
     * 插入数据后平衡操作
     * <p>
     * 分析： 插入的节点默认为红色，如果父节点为红色 那么就不满足红黑树的条件(红色节点有两个黑子节点)。所以当父节点为红色的时候需要调整，主要分以下三种情况(以p的父节点是爷爷的左子节点为例)
     * 一.p的父节点和叔叔节点都是红的
     * 解决：将p的父节点和叔叔节点变为黑色，p的爷爷节点变为红色。但p的爷爷节点的父节点可能也是红的那就又不满足红黑树的原则了，此时就需要把p的爷爷节点当作p节点继续往上递归了
     * 二.p的叔叔节点不是红色的(或者没有叔叔节点)，并且p是父亲的左节点
     * 解决：以p的爷爷节点为支点向右旋
     * 三.p的叔叔节点不是红色的(或者没有叔叔节点)，并且p是父亲的右节点
     * 解决：先以p的父亲节点左旋，变成了情况二了，以p的爷爷节点为支点向右旋
     *
     * @param p 插入的节点
     */
    private void balanceInsertion(RBNode<K, V> p) {
        //p不是根节点 并且p的父节点是红色的时候需要调整
        while (p != null && p != root && p.parent.red) {
            //父节点在左边
            if (p.parent == p.parent.parent.left) {
                //叔叔节点
                RBNode<K, V> u;
                //如果叔叔节点是红色的（情况一）
                if ((u = p.parent.parent.right) != null && u.red) {
                    //父节点和叔叔节点变黑  爷爷节点变红  然后以爷爷节点继续往上递归
                    u.red = false;
                    p.parent.red = false;
                    p.parent.parent.red = true;
                    //以爷爷节点继续往上递归
                    p = p.parent.parent;
                } else {
                    //p是父亲的右节点（情况三）
                    if (p == p.parent.right) {
                        p = p.parent;
                        //以p的父亲节点左旋
                        rotateLeft(p);
                    }
                    //爷爷变红色
                    p.parent.parent.red = true;
                    //父亲边黑色
                    p.parent.red = false;
                    //以p的爷爷节点右旋
                    rotateRight(p.parent.parent);

                }
            } else {
                RBNode<K, V> u;
                if ((u = p.parent.parent.left) != null && u.red) {
                    u.red = false;
                    p.parent.red = false;
                    p.parent.parent.red = true;
                    p = p.parent.parent;
                } else {
                    if (p == p.parent.left) {
                        p = p.parent;
                        rotateRight(p);
                    }
                    p.parent.parent.red = true;
                    p.parent.red = false;
                    rotateLeft(p.parent.parent);

                }
            }
        }
        //根节点变黑色
        root.red = false;
    }

    /**
     * 根据key移除某个节点
     *
     * @param kay
     */
    public void remove(K kay) {
        //根据key查找对应的值
        RBNode<K, V> p = getNodeByKey(kay);

        if (p == null) return;
        //如果p的左右节点都存在
        if (p.left != null && p.right != null) {
            //找到前驱节点
            RBNode<K, V> node = predecessor(p);
            //将node值赋给p
            p.k = node.k;
            p.v = node.v;
            p = node;
        }
        //p节点的替换节点（也就是p的子节点，如果存在只可能有一个）
        RBNode<K, V> replace = p.left != null ? p.left : p.right;
        if (replace != null) {
            RBNode<K, V> pp;
            //如果p节点的父节点是根节点 那么替换节点变为根节点
            if ((pp = p.parent) == null) {
                root = replace;
            } else if (p == pp.left) {
                pp.left = replace;
            } else {
                pp.right = replace;
            }
            replace.parent = pp;

            //删除p节点
            p.left = p.right = p.parent = null;
            //删除的是黑色节点需要调整
            if (!p.red) {
                //对replace 进行平衡处理
                balanceDeletion(replace);
            }
        } else if (p.parent == null) {
            root = null;
        } else {//p就是根节点，这个时候需要先调整平衡 再做删除
            if (!p.red) {
                balanceDeletion(p);
            }
            //删除p节点
            if (p.parent.left == p) {
                p.parent.left = null;
            } else {
                p.parent.right = null;
            }
            p.parent = null;
        }
    }

    /**
     * 根据key找到对应的节点
     *
     * @param k 寻找的key
     * @return 找到的节点值
     */
    private RBNode<K, V> getNodeByKey(K k) {
        if (k != null) {
            RBNode<K, V> temp = root;
            do {
                if (k.compareTo(temp.k) < 0) {
                    temp = temp.left;
                } else if (k.compareTo(temp.k) > 0) {
                    temp = temp.right;
                } else {
                    return temp;
                }
            } while (temp != null);
        }
        return null;
    }


    /**
     * 找到前驱节点(小于当前节点的最大值)
     *
     * @param node
     * @return
     */
    private RBNode<K, V> predecessor(RBNode<K, V> node) {
        if (node != null) {
            //判断node左节点是否存在
            if (node.left != null) {
                RBNode<K, V> plr;
                if ((plr = node.left.right) != null) {
                    //找到左节点的最右节点
                    while (plr.right != null) {
                        plr = plr.right;
                    }
                    return plr;
                }
                return node.left;
            } else {//如果不存在
                RBNode<K, V> child = node;
                RBNode<K, V> p = node.parent;
                while (p != null && child == p.left) {
                    child = p;
                    p = p.parent;
                }
                return p;
            }
        }
        return null;
    }

    /**
     * 找到后继节点(大于当前节点的最小值)
     *
     * @param node
     * @return Successor
     */
    private RBNode<K, V> successor(RBNode<K, V> node) {
        if (node != null) {
            if (node.right != null) {
                RBNode<K, V> pll;
                if ((pll = node.right.left) != null) {
                    while (pll.left != null) {
                        pll = pll.left;
                    }
                    return pll;
                }
                return node.right;
            } else {
                RBNode<K, V> child = node;
                RBNode<K, V> p = node.parent;
                while (p != null && child == p.right) {
                    child = p;
                    p = p.parent;
                }
                return p;
            }
        }
        return null;
    }


    /**
     * 删除调整
     *
     * @param p 调整的节点
     */
    private void balanceDeletion(RBNode<K, V> p) {
        //p不等于根节点并且p是黑色
        while (p != root && !getColor(p)) {
            //p是父亲的左节点
            if (p == getLeft(getParent(p))) {
                //叔叔节点
                RBNode<K, V> ppr;
                //如果叔叔节点是红色,两个子节点是黑色 不能直接借
                if ((ppr = getRight(getParent(p))) != null && getColor(ppr)) {
                    //叔叔节点变黑色
                    setColor(ppr, false);
                    //p父节点变红
                    setColor(getParent(p), true);
                    //围绕p的父节点左旋
                    rotateLeft(getParent(p));
                    ppr = getRight(getParent(p));
                }
                //判断叔叔节点是否存在两个黑色节点(或者两个子节点都为空)
                if (!getColor(getLeft(ppr)) && !getColor(getRight(ppr))) {
                    //将叔叔点变成红色
                    setColor(ppr, true);
                    //根据p的父节点继续往上递归
                    p = getParent(p);
                } else {
                    //叔叔节点的右节点如果是黑色
                    if (!getColor(getRight(ppr))) {
                        //叔叔节点变红
                        setColor(ppr, true);
                        //叔叔节点的左节点变黑
                        setColor(getLeft(ppr), false);
                        //将叔叔节点右旋
                        rotateRight(ppr);
                        ppr = getRight(getParent(p));
                    }
                    //叔叔节点颜色变为p父节点颜色
                    setColor(ppr, getColor(getParent(p)));
                    //p父节点变黑
                    setColor(getParent(p), false);
                    //叔叔节点的右节点变黑
                    setColor(getRight(ppr), false);
                    //根据p的父节点节点左旋
                    rotateLeft(getParent(p));
                    break;
                }
            } else {
                RBNode<K, V> ppl;
                if ((ppl = getLeft(getParent(p))) != null && getColor(ppl)) {
                    setColor(ppl, false);
                    setColor(getParent(p), true);
                    rotateRight(getParent(p));
                    ppl = getLeft(getParent(p));
                }
                if (!getColor(getLeft(ppl)) && !getColor(getRight(ppl))) {
                    setColor(ppl, true);
                    p = getParent(p);
                } else {
                    if (!getColor(getLeft(ppl))) {
                        setColor(ppl, true);
                        setColor(getRight(ppl), false);
                        rotateLeft(ppl);
                        ppl = getLeft(getParent(p));
                    }
                    setColor(ppl, getColor(getParent(p)));
                    setColor(getParent(p), false);
                    setColor(getLeft(ppl), false);
                    rotateRight(getParent(p));
                    break;
                }
            }

        }
        //将p节点变为黑色
        setColor(p, false);

    }

    /**
     * 获取节点的颜色
     *
     * @param node
     * @return 如果节点为空 返回false(黑色) 否则返回节点的颜色
     */
    private boolean getColor(RBNode<K, V> node) {
        return node != null && node.red;
    }

    private RBNode<K, V> getParent(RBNode<K, V> node) {
        return node != null ? node.parent : null;
    }

    private RBNode<K, V> getLeft(RBNode<K, V> node) {
        return node != null ? node.left : null;
    }

    private RBNode<K, V> getRight(RBNode<K, V> node) {
        return node != null ? node.right : null;
    }

    private void setColor(RBNode<K, V> node, boolean color) {
        if (node != null) {
            node.red = color;
        }
    }


}
