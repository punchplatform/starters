package org.thales.punch.spark.node.starter.kit;

import java.util.Optional;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.thales.punch.api.PunchUncheckedException;
import org.thales.punch.pml.configuration.NodeName;
import org.thales.punch.pml.configuration.exceptions.PMLException;
import org.thales.punch.pml.configuration.scanner.ScanNode;
import org.thales.punch.pml.configuration.scanner.ScanNode.ScanType;
import org.thales.punch.pml.job.graph.IDeclarer;
import org.thales.punch.pml.job.graph.IInputDatasetHolder;
import org.thales.punch.pml.job.graph.INode;
import org.thales.punch.pml.job.graph.IOutputDatasetHolder;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * <pre>
 * PREREQUISITE: 
 * 
 * - A STANDALONE installed
 * - update pom.xml to your STANDALONE version (punch.version key)
 * - use punchplatform-developpment.sh --install to add necessary maven artifacts to your .m2 folder
 * - package name should be org.thales, org.punch; else you should use package key in your node settings
 * 
 * {
 * 		type: my_node
 * 		package: org.thales.punch.spark.node.starter.kit
 * 		....
 * }
 * 
 * </pre>
 * Empty project where you can write your PUNCHLINE Spark Node
 * <p>
 * @author jonathan
 *
 */
@NodeName("my_node")
@ScanNode(type = ScanType.OUTPUT_NODE)
public class MyNode
	implements INode {

	private static final long serialVersionUID = 1L;

	@Override
	public void execute(
		IInputDatasetHolder input, 
		IOutputDatasetHolder output) 
			throws PMLException {
		// print to stdout example
		Optional<Dataset<Row>> show = input
				.getSingletonDataframe();
		if (show.isPresent()) {
			show
				.get()
				.show();
		} else {
			throw new PunchUncheckedException("null detected");
		}
	}

	@Override
	public void declare(
		IDeclarer declarer) {
		declarer
			.subscribeSingleton(
				new TypeReference<Dataset<Row>>() {});
	}

}
