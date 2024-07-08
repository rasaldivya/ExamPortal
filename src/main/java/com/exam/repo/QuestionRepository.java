package com.exam.repo;

import com.exam.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Long> {
//    Set<Question> findByQuiz(Quiz quiz);
List<Question> findByTrade(String trade);

    @Query("SELECT DISTINCT q.trade FROM Question q")
    List<String> findAllTrades();

    @Query("SELECT DISTINCT q.topic FROM Question q")
    List<String> findAllTopics();

    List<Question> findByTopicAndType(String topic, String type);

    List<Question> findByTopicAndTypeAndTrade(String topic, String type, String trade);

    @Query("SELECT DISTINCT q.topic FROM Question q WHERE q.trade = :trade")
    List<String> findAllUniqueTopicsByTrade(String trade);


    @Query("SELECT q.topic, q FROM Question q")
    List<Object[]> findAllQuestionsGroupedByTopic();

    @Query("SELECT q.topic, q.type, COUNT(q), q FROM Question q GROUP BY q.topic, q.type")
    List<Object[]> findAllQuestionsGroupedByTopicAndTypeWithCount();

    @Query("SELECT q.topic, q.type, q, COUNT(q) FROM Question q GROUP BY q.topic, q.type")
    List<Object[]> findAllQuestionsGroupedByTopicAndTypeWithCountAndData();


}
