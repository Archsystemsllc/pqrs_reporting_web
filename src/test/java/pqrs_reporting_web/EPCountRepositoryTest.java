package pqrs_reporting_web;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIn.isIn;
import static org.hamcrest.core.IsNot.not;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.archsystemsinc.pqrs.PersistenceConfig;
import com.archsystemsinc.pqrs.model.AreaType;
import com.archsystemsinc.pqrs.model.BarChartData;
import com.archsystemsinc.pqrs.model.Dataset;
import com.archsystemsinc.pqrs.model.EPStateCount;
import com.archsystemsinc.pqrs.model.EPSummary;
import com.archsystemsinc.pqrs.model.IChart;
import com.archsystemsinc.pqrs.model.annotation.XAxis;
import com.archsystemsinc.pqrs.model.annotation.YAxis;
import com.archsystemsinc.pqrs.model.annotation.ZAxis;
import com.archsystemsinc.pqrs.queryprocessor.CustomRsqlVisitor;
import com.archsystemsinc.pqrs.repository.EPStateCountRepository;
import com.archsystemsinc.pqrs.repository.EPSummaryRepository;
import com.archsystemsinc.pqrs.utils.ApplicationUtils;
import com.fasterxml.jackson.core.JsonProcessingException;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfig.class })
@Transactional
@TransactionConfiguration
public class EPCountRepositoryTest {
 
    @Autowired
    private EPStateCountRepository repository;
    
    @Autowired
    private EPSummaryRepository ePSummaryRepository;
 
    private EPStateCount firstEPCount;
 
    private EPStateCount secondEPCount;
 
    @Before
    public void init() {
    	firstEPCount = new EPStateCount();
        firstEPCount.setAreaType(AreaType.URBAN.name());
        firstEPCount.setNumberOfEPs(25);
        firstEPCount.setStateCode("Maryland");
        repository.save(firstEPCount);
 
        secondEPCount = new EPStateCount();
        secondEPCount.setAreaType(AreaType.RURAL.name());
        secondEPCount.setNumberOfEPs(15);
        secondEPCount.setStateCode("New Mexico");
        repository.save(secondEPCount);
    }
    
    @Test
    public void testIt() {
    	
    }
    @Test
    public void givenZipCodeAndAreaType_whenGettingListOfEPCounts_thenCorrect() {
        Node rootNode = new RSQLParser().parse("zipCode==21333;areaType==URBAN");
        Specification<EPStateCount> spec = rootNode.accept(new CustomRsqlVisitor<EPStateCount>());
        List<EPStateCount> results = repository.findAll(spec);
        assertThat(firstEPCount, isIn(results));
        assertThat(secondEPCount, not(isIn(results)));
    }
     
    @Test
	public void findAllForChartsByRsql() throws JsonProcessingException, IllegalArgumentException, IllegalAccessException {
		Node rootNode = new RSQLParser().parse("type==All");
		Specification<EPSummary> spec = rootNode.accept(new CustomRsqlVisitor<EPSummary>());
		List<EPSummary> epSummaryData = ePSummaryRepository.findAll(spec);
		
		ApplicationUtils.getBarChartData(epSummaryData, EPSummary.class);

	}
}