package com.tonytor.activityservice;

import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ActivityTaskServiceApplicationTests {

	/*NodeHolder holder;
	Node node1;
	Node node2;
	Node node3;
	Node node4;
	Node node5;
	Node node6;
	Node node7;
	Node node8;
	Node node9;
	Node node10;
	Node node11;
	Node node12;

	@Test
	void test1p1() {
		holder = new NodeHolder();
		node1 = new Node(holder, "Node1");

		List<Node> roots = holder.getRoots();
		List<Node> leaves = holder.getLeaves();
		assertThat(roots).contains(node1);
		assertThat(leaves).contains(node1);

		node2 = new Node(holder, "Node2");
		node1.addChild(node2);

		roots = holder.getRoots();
		leaves = holder.getLeaves();

		assertThat(roots).contains(node1);
		assertThat(roots).doesNotContain(node2);

		assertThat(leaves).doesNotContain(node1);
		assertThat(leaves).contains(node2);

		node3 = new Node(holder, "Node3");
		node1.addChild(node3);

		roots = holder.getRoots();
		leaves = holder.getLeaves();

		assertThat(roots).contains(node1);
		assertThat(roots).doesNotContain(node2, node3);

		assertThat(leaves).contains(node2, node3);
		assertThat(leaves).doesNotContain(node1);

		node4 = new Node(holder, "Node4");
		node1.addChild(node4);

		roots = holder.getRoots();
		leaves = holder.getLeaves();

		assertThat(roots).contains(node1);
		assertThat(roots).doesNotContain(node2, node3, node4);

		assertThat(leaves).contains(node2, node3, node4);
		assertThat(leaves).doesNotContain(node1);

		node5 = new Node(holder, "Node5");
		node3.addChild(node5);

		roots = holder.getRoots();
		leaves = holder.getLeaves();

		assertThat(roots).contains(node1);
		assertThat(roots).doesNotContain(node2, node3, node4, node5);

		assertThat(leaves).contains(node2, node4, node5);
		assertThat(leaves).doesNotContain(node1, node3);

		node6 = new Node(holder, "Node6");
		node3.addChild(node6);

		roots = holder.getRoots();
		leaves = holder.getLeaves();

		assertThat(roots).contains(node1);
		assertThat(roots).doesNotContain(node2, node3, node4, node5, node6);

		assertThat(leaves).contains(node2, node4, node5, node6);
		assertThat(leaves).doesNotContain(node1, node3);

		node7 = new Node(holder, "Node7");
		node4.addChild(node7);

		roots = holder.getRoots();
		leaves = holder.getLeaves();

		assertThat(roots).contains(node1);
		assertThat(roots).doesNotContain(node2, node3, node4, node5, node6, node7);

		assertThat(leaves).contains(node2, node5, node6, node7);
		assertThat(leaves).doesNotContain(node1, node3, node4);

		node8 = new Node(holder, "Node8");
		node4.addChild(node8);

		roots = holder.getRoots();
		leaves = holder.getLeaves();

		assertThat(roots).contains(node1);
		assertThat(roots).doesNotContain(node2, node3, node4, node5, node6, node7, node8);

		assertThat(leaves).contains(node2, node5, node6, node7, node8);
		assertThat(leaves).doesNotContain(node1, node3, node4);

		node9 = new Node(holder, "Node9");
		node7.addChild(node9);

		roots = holder.getRoots();
		leaves = holder.getLeaves();

		assertThat(roots).contains(node1);
		assertThat(roots).doesNotContain(node2, node3, node4, node5, node6, node7, node8, node9);

		assertThat(leaves).contains(node2, node5, node6, node8, node9);
		assertThat(leaves).doesNotContain(node1, node3, node4, node7);

		node10 = new Node(holder, "Node10");
		node7.addChild(node10);

		roots = holder.getRoots();
		leaves = holder.getLeaves();

		assertThat(roots).contains(node1);
		assertThat(roots).doesNotContain(node2, node3, node4, node5, node6, node7, node8, node9, node10);

		assertThat(leaves).contains(node2, node5, node6, node8, node9, node10);
		assertThat(leaves).doesNotContain(node1, node3, node4, node7);

		node11 = new Node(holder, "Node11");
		node8.addChild(node11);

		roots = holder.getRoots();
		leaves = holder.getLeaves();

		assertThat(roots).contains(node1);
		assertThat(roots).doesNotContain(node2, node3, node4, node5, node6, node7, node8, node9, node10, node11);

		assertThat(leaves).contains(node2, node5, node6, node9, node10, node11);
		assertThat(leaves).doesNotContain(node1, node3, node4, node7, node8);

		node12 = new Node(holder, "Node12");

		roots = holder.getRoots();
		leaves = holder.getLeaves();

		assertThat(roots).contains(node1, node12);
		assertThat(roots).doesNotContain(node2, node3, node4, node5, node6, node7, node8, node9, node10, node11);

		assertThat(leaves).contains(node2, node5, node6, node9, node10, node11, node12);
		assertThat(leaves).doesNotContain(node1, node3, node4, node7, node8);

		node4.cutBranch();

		roots = holder.getRoots();
		leaves = holder.getLeaves();

		assertThat(roots).contains(node1, node12, node4);
		assertThat(roots).doesNotContain(node2, node3, node5, node6, node7, node8, node9, node10, node11);

		assertThat(leaves).contains(node2, node5, node6, node9, node10, node11, node12);
		assertThat(leaves).doesNotContain(node1, node3, node4, node7, node8);

		node3.addChild(node4);

		roots = holder.getRoots();
		leaves = holder.getLeaves();

		assertThat(roots).contains(node1, node12);
		assertThat(roots).doesNotContain(node2, node3, node4, node5, node6, node7, node8, node9, node10, node11);

		assertThat(leaves).contains(node2, node5, node6, node9, node10, node11, node12);
		assertThat(leaves).doesNotContain(node1, node3, node4, node7, node8);

		node4.cutBranch();

		roots = holder.getRoots();
		leaves = holder.getLeaves();

		assertThat(roots).contains(node1, node12, node4);
		assertThat(roots).doesNotContain(node2, node3, node5, node6, node7, node8, node9, node10, node11);

		assertThat(leaves).contains(node2, node5, node6, node9, node10, node11, node12);
		assertThat(leaves).doesNotContain(node1, node3, node4, node7, node8);

		node5.addChild(node4);

		roots = holder.getRoots();
		leaves = holder.getLeaves();

		assertThat(roots).contains(node1, node12);
		assertThat(roots).doesNotContain(node2, node3, node4, node5, node6, node7, node8, node9, node10, node11);

		assertThat(leaves).contains(node2, node6, node9, node10, node11, node12);
		assertThat(leaves).doesNotContain(node1, node5, node3, node4, node7, node8);

		node5.removeChild(node4);

		roots = holder.getRoots();
		leaves = holder.getLeaves();

		assertThat(roots).contains(node1, node12, node4);
		assertThat(roots).doesNotContain(node2, node3, node5, node6, node7, node8, node9, node10, node11);

		assertThat(leaves).contains(node2, node5, node6, node9, node10, node11, node12);
		assertThat(leaves).doesNotContain(node1, node3, node4, node7, node8);

		node5.setParent(node4);

		roots = holder.getRoots();
		leaves = holder.getLeaves();

		assertThat(roots).contains(node1, node12, node4);
		assertThat(roots).doesNotContain(node2, node3, node5, node6, node7, node8, node9, node10, node11);

		assertThat(leaves).contains(node2, node5, node6, node9, node10, node11, node12);
		assertThat(leaves).doesNotContain(node1, node3, node4, node7, node8);

		node5.setParent(null);

		roots = holder.getRoots();
		leaves = holder.getLeaves();

		assertThat(roots).contains(node1, node12, node4, node5);
		assertThat(roots).doesNotContain(node2, node3, node6, node7, node8, node9, node10, node11);

		assertThat(leaves).contains(node2, node5, node6, node9, node10, node11, node12);
		assertThat(leaves).doesNotContain(node1, node3, node4, node7, node8);

	}*/

}
