package pqrs_reporting_web;

import java.util.List;
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

import com.archsystemsinc.pqrs.PersistenceConfig;
import com.archsystemsinc.pqrs.model.AreaType;
import com.archsystemsinc.pqrs.model.EPCount;
import com.archsystemsinc.pqrs.queryprocessor.CustomRsqlVisitor;
import com.archsystemsinc.pqrs.repository.EPCountRepository;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfig.class })
@Transactional
@TransactionConfiguration
public class EPCountRepositoryTest {
 
    @Autowired
    private EPCountRepository repository;
 
    private EPCount firstEPCount;
 
    private EPCount secondEPCount;
 
    @Before
    public void init() {
    	firstEPCount = new EPCount();
        firstEPCount.setAreaType(AreaType.URBAN.name());
        firstEPCount.setNumberOfEPs(25);
        firstEPCount.setZipCode("21333");
        repository.save(firstEPCount);
 
        secondEPCount = new EPCount();
        secondEPCount.setAreaType(AreaType.RURAL.name());
        secondEPCount.setNumberOfEPs(15);
        secondEPCount.setZipCode("21424");
        repository.save(secondEPCount);
    }
    
    @Test
    public void givenZipCodeAndAreaType_whenGettingListOfEPCounts_thenCorrect() {
        Node rootNode = new RSQLParser().parse("zipCode==21333;areaType==URBAN");
        Specification<EPCount> spec = rootNode.accept(new CustomRsqlVisitor<EPCount>());
        List<EPCount> results = repository.findAll(spec);
        assertThat(firstEPCount, isIn(results));
        assertThat(secondEPCount, not(isIn(results)));
    }
}