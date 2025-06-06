/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.metaverse.impl.model;

import org.pentaho.metaverse.api.model.BaseInfo;
import org.pentaho.metaverse.api.model.IExecutionData;
import org.pentaho.metaverse.api.model.IExecutionEngine;
import org.pentaho.metaverse.api.model.IExecutionProfile;

public class ExecutionProfile extends BaseInfo implements IExecutionProfile {

  private String path;
  private String type;
  private IExecutionEngine executionEngine = new ExecutionEngine();
  private IExecutionData executionData = new ExecutionData();

  public ExecutionProfile() {
    super();
  }

  public ExecutionProfile( String name, String path, String type, String description ) {
    this();
    this.setName( name );
    this.setDescription( description );
    this.path = path;
    this.type = type;
  }

  /**
   * Returns the path to the artifact represented by this execution profile
   *
   * @return a string representing the path to the execution profile's associated artifact
   */
  @Override
  public String getPath() {
    return path;
  }

  /**
   * Sets the path for the artifact represented by this execution profile
   *
   * @param path the path to the artifact for this execution profile
   */
  @Override
  public void setPath( String path ) {
    this.path = path;
  }

  /**
   * Returns the type of the artifact represented by this execution profile
   *
   * @return a string representing the type of the execution profile's associated artifact
   */
  @Override
  public String getType() {
    return type;
  }

  /**
   * Sets the type of the artifact represented by this execution profile
   *
   * @param type the type of the artifact for this execution profile
   */
  @Override
  public void setType( String type ) {
    this.type = type;
  }

  /**
   * Returns the engine used to execute the associated artifact
   *
   * @return the execution engine associated with the execution profile's artifact
   */
  @Override
  public IExecutionEngine getExecutionEngine() {
    return executionEngine;
  }

  /**
   * Sets the engine used to execute the associated artifact
   *
   * @param executionEngine the engine associated with executing this execution profile's artifact
   */
  @Override
  public void setExecutionEngine( IExecutionEngine executionEngine ) {
    this.executionEngine = executionEngine;
  }

  /**
   * Returns the data associated with this execution of the artifact
   *
   * @return the execution data associated with the execution profile's artifact
   */
  @Override
  public IExecutionData getExecutionData() {
    return executionData;
  }

  /**
   * Sets the data associated with this execution of the artifact
   *
   * @param executionData the data associated with executing this execution profile's artifact
   */
  @Override
  public void setExecutionData( IExecutionData executionData ) {
    this.executionData = executionData;
  }

}
