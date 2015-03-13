package com.pentaho.metaverse.analyzer.kettle;

import com.pentaho.dictionary.DictionaryConst;
import com.pentaho.metaverse.testutils.MetaverseTestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.pentaho.di.trans.steps.mongodb.MongoDbMeta;
import org.pentaho.platform.api.metaverse.IAnalysisContext;
import org.pentaho.platform.api.metaverse.IComponentDescriptor;
import org.pentaho.platform.api.metaverse.IMetaverseBuilder;
import org.pentaho.platform.api.metaverse.IMetaverseNode;
import org.pentaho.platform.api.metaverse.IMetaverseObjectFactory;
import org.pentaho.platform.api.metaverse.INamespace;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * User: RFellows Date: 3/6/15
 */
@RunWith( MockitoJUnitRunner.class )
public class MongoDbConnectionAnalyzerTest {

  MongoDbConnectionAnalyzer analyzer;

  @Mock private IMetaverseBuilder mockBuilder;
  @Mock private MongoDbMeta mongoDbMeta;
  @Mock private IComponentDescriptor mockDescriptor;

  @Before
  public void setUp() throws Exception {
    IMetaverseObjectFactory factory = MetaverseTestUtils.getMetaverseObjectFactory();
    when( mockBuilder.getMetaverseObjectFactory() ).thenReturn( factory );

    analyzer = new MongoDbConnectionAnalyzer();
    analyzer.setMetaverseBuilder( mockBuilder );

    when( mockDescriptor.getNamespace() ).thenReturn( mock( INamespace.class) );
    when( mockDescriptor.getContext() ).thenReturn( mock( IAnalysisContext.class ) );

    when( mongoDbMeta.getHostnames() ).thenReturn( "localhost" );
    when( mongoDbMeta.getDbName() ).thenReturn( "db" );
    when( mongoDbMeta.getAuthenticationUser() ).thenReturn( "user" );
    when( mongoDbMeta.getPort() ).thenReturn( "12345" );
  }

  @Test
  public void testAnalyze() throws Exception {
    when( mockBuilder.addNode( any( IMetaverseNode.class ) ) ).thenAnswer( new Answer<Object>() {
      @Override public Object answer( InvocationOnMock invocation ) throws Throwable {
        Object[] args = invocation.getArguments();
        // add the logicalId to the node like it does in the real builder
        IMetaverseNode node = (IMetaverseNode)args[0];
        node.setProperty( DictionaryConst.PROPERTY_LOGICAL_ID, node.getLogicalId() );
        return mockBuilder;
      }
    } );

    IMetaverseNode node = analyzer.analyze( mockDescriptor, mongoDbMeta );
    assertNotNull( node );
    assertEquals( "localhost", node.getProperty( MongoDbConnectionAnalyzer.HOST_NAMES ) );
    assertEquals( "db", node.getProperty( MongoDbConnectionAnalyzer.DATABASE_NAME ) );
    assertEquals( "user", node.getProperty( DictionaryConst.PROPERTY_USER_NAME ) );
    assertEquals( "12345", node.getProperty( DictionaryConst.PROPERTY_PORT ) );

  }

  @Test
  public void testGetUsedConnections() throws Exception {
    List<MongoDbMeta> dbMetaList = analyzer.getUsedConnections( mongoDbMeta );
    assertEquals( 1, dbMetaList.size() );

    // should just return the same MongoDbMeta object in list form as the only entry
    assertEquals( mongoDbMeta, dbMetaList.get( 0 ) );
  }

  @Test
  public void testBuildComponentDescriptor() throws Exception {
    IComponentDescriptor dbDesc = analyzer.buildComponentDescriptor( mockDescriptor, mongoDbMeta );
    assertNotNull( dbDesc );
    assertEquals( "db", dbDesc.getName() );
  }
}