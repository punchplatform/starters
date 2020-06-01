package org.thales.punch.spark.node.starter.kit;

import org.thales.punch.api.PunchUncheckedException;
import org.thales.punch.pml.configuration.exceptions.PMLException;
import org.thales.punch.pml.job.graph.IDeclarer;
import org.thales.punch.pml.job.graph.IInputDatasetHolder;
import org.thales.punch.pml.job.graph.INode;
import org.thales.punch.pml.job.graph.IOutputDatasetHolder;

/**
 * <pre>
 * PREREQUISITE: 
 * 
 * - A STANDALONE installed
 * - update pom.xml to your STANDALONE version (punch.version key)
 * - use punchplatform-developpment.sh --install to add necessary maven artifacts to your .m2 folder
 * 
 * </pre>
 * Empty project where you can write your PUNCHLINE Spark Node
 * <p>
 * @author jonathan
 *
 */
public class MyNode
	implements INode {

	private static final long serialVersionUID = 1L;

	@Override
	public void execute(
		IInputDatasetHolder input, 
		IOutputDatasetHolder output) 
			throws PMLException {
		throw new PunchUncheckedException("Not implemented");
	}

	@Override
	public void declare(
		IDeclarer declarer) {
		throw new PunchUncheckedException("Not implemented");
	}

}
