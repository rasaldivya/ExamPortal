package com.exam.services.implement;
import com.exam.entity.*;
import com.exam.exceptions.NotFoundException;
import com.exam.model.exams.*;
//import com.exam.model.exams.BatchUsers;
import com.exam.repo.*;
//import com.exam.repo.BatchUsersRepository;
import com.exam.services.BatchService;
import com.exam.services.QuizService;


import com.exam.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.exam.utils.AuthUtils.getCurrentPrincipal;

@Repository
public class QuizServiceImpl implements QuizService {

    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private QuestionRepository questionRepo;
    @Autowired
    private BatchRepository batchRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BatchUserMappingRepo batchUserMappingRepo;
	@Autowired
	UserService userService;
	@Autowired
	BatchService batchService;
	@Autowired
	ResultRepository resultRepository;

//	@Autowired
//	 TraineeBatchModel traineeBatchMode;
    @Override
    public Quiz addQuiz(QuizModel quizModel) {
    	
    	Set<Question> questions=quizModel.getQuestionsOfQuiz();

    	System.out.println("Quiz Name"+quizModel);
    	Iterator<Question> i=questions.iterator();
    	int x=0;
    	String questString="";
    	while(i.hasNext()) {
			Question q=i.next();
    		questString=questString.concat(q.getQuesId()+",");
    	}
		System.out.println("All questions are :  "+questString);
		Quiz quiz = getModelToQuiz(quizModel);
		quiz.setQuestionsOfQuiz(questString);

		Quiz savedQuiz = this.quizRepository.save(quiz);

		if(quizModel.getBatches()!=null) {
			quizModel.getBatches().forEach(batchModel -> {
				Batch batch = new Batch();
				BeanUtils.copyProperties(batchModel, batch);

				List<User> retrievedUsers = userRepository.findAllById(batchModel.getUserIds());
				List<BatchUserMapping> batchUserMappings = new ArrayList<>();
				Batch savedBatch = batchRepository.save(batch);
				retrievedUsers.forEach(user -> {
							BatchUserMapping batchUserMapping = new BatchUserMapping();
							batchUserMapping.setBatch(savedBatch);
							batchUserMapping.setQuizStatus("CREATED");
							batchUserMapping.setUser(user);
							batchUserMappings.add(batchUserMapping);
						}
				);

				batchUserMappingRepo.saveAll(batchUserMappings);


			});
		}

        return savedQuiz;
    }


	public QuizModel updateQuiz(Long quizId, Quiz updatedQuiz) throws NotFoundException {
		Optional<Quiz> optionalQuiz = quizRepository.findById(quizId);
		if (optionalQuiz.isPresent()) {
			Quiz existingQuiz = optionalQuiz.get();

			// Update fields if provided in the updatedQuiz
			if (updatedQuiz.getName() != null) {
				existingQuiz.setName(updatedQuiz.getName());
			}
			if (updatedQuiz.getDescription() != null) {
				existingQuiz.setDescription(updatedQuiz.getDescription());
			}
			if (updatedQuiz.isActive() != existingQuiz.isActive()) {
				existingQuiz.setActive(updatedQuiz.isActive());
			}
			if (updatedQuiz.getMarkOfQuestion() != null) {
				existingQuiz.setMarkOfQuestion(updatedQuiz.getMarkOfQuestion());
			}
			if (updatedQuiz.getMaxMarks() != null) {
				existingQuiz.setMaxMarks(updatedQuiz.getMaxMarks());
			}
			if (updatedQuiz.getNoOfQuestions() != null) {
				existingQuiz.setNoOfQuestions(updatedQuiz.getNoOfQuestions());
			}
			if (updatedQuiz.getQuizTime() != null) {
				existingQuiz.setQuizTime(updatedQuiz.getQuizTime());
			}
//			if (updatedQuiz.getQuestionsOfQuiz() != null) {
//				existingQuiz.setQuestionsOfQuiz(updatedQuiz.getQuestionsOfQuiz());
//			}
			if (updatedQuiz.isNegativeMarks() != existingQuiz.isNegativeMarks()) {
				existingQuiz.setNegativeMarks(updatedQuiz.isNegativeMarks());
			}
			if (updatedQuiz.getTrade() != null) {
				existingQuiz.setTrade(updatedQuiz.getTrade());
			}
			// Similarly, update other fields as needed

			// Save and return the updated quiz
			quizRepository.save(existingQuiz);
			return getQuiz(quizId);
		} else {
			throw new NotFoundException("Quiz not found with id: " + quizId);
		}
	}
	public QuizModel updateQuizStatus(Long quizId, boolean status) throws NotFoundException {
		Optional<Quiz> optionalQuiz = quizRepository.findById(quizId);
		if (optionalQuiz.isPresent()) {
			Quiz existingQuiz = optionalQuiz.get();

			if (status != existingQuiz.isActive()) {
				existingQuiz.setActive(status);
			}

			// Save and return the updated quiz
			quizRepository.save(existingQuiz);
			return getQuiz(quizId);
		} else {
			throw new NotFoundException("Quiz not found with id: " + quizId);
		}
	}

    @Override
    public List<QuizModel> getQuizzes(String trade) {

		List<Quiz> quiz;
		if(trade==null){
			quiz=quizRepository.findAllByOrderByCreatedAtDesc();
		}else
		{
			quiz=quizRepository.findByTradeOrderByCreatedAtDesc(Optional.of(trade));
		}


		List<QuizModel> quizModels=new ArrayList<>();
		quiz.forEach(q->{
			quizModels.add(getQuiz(q.getQuizId()));
		});
		return quizModels;

	}
	@Override
	public List<TraineeBatchModel> getTraineeBatchModel(){

		UserDetails userDetails= getCurrentPrincipal();
		User user= userService.getUserByArmyNo(userDetails.getUsername());

		List<BatchUserMapping> batchMappings=batchUserMappingRepo.findByUser(user);
		List<Long> quizIds=new ArrayList<>();
		List<TraineeBatchModel> traineeBatchModels =new ArrayList<>();
		batchMappings.forEach(batchMapping->{
			Batch batch =batchMapping.getBatch();
			//Quiz quiz=batch.getQuiz();
			//quizIds.add(batch.getQuiz().getQuizId());
			TraineeBatchModel traineeBatchModel =new TraineeBatchModel(getQuiz(batch.getQuiz().getQuizId()),batch);
			traineeBatchModel.setBatchUserStatus(batchMapping.getQuizStatus());
			traineeBatchModels.add(traineeBatchModel);
		});

		List<Quiz> quiz=quizRepository.findAllById(quizIds);
		List<QuizModel> quizModels=new ArrayList<>();
		quiz.forEach(q->{
			quizModels.add(getQuiz(q.getQuizId()));
		});
		return traineeBatchModels;


	}

	@Override
	public List<QuizModel> getQuizzesTrainee() {

		UserDetails userDetails= getCurrentPrincipal();
		System.out.println("Quiz Model");
		System.out.println(userDetails.getUsername());

		User user= userService.getUserByArmyNo(userDetails.getUsername());
// get allbathes by userid form batchusermapping

		List<BatchUserMapping> batchMappings=batchUserMappingRepo.findByUser(user);
		List<Long> quizIds=new ArrayList<>(); 
		batchMappings.forEach(batchMapping->{
			Batch batch =batchMapping.getBatch();
			//Quiz quiz=batch.getQuiz();
			quizIds.add(batch.getQuiz().getQuizId());
		
		});
		




		List<Quiz> quiz=quizRepository.findAllById(quizIds);
		List<QuizModel> quizModels=new ArrayList<>();
		quiz.forEach(q->{
			quizModels.add(getQuiz(q.getQuizId()));
		});
		return quizModels;

	}

    @Override
    public QuizModel getQuiz(Long quizId) {
    	
    	Quiz quiz=quizRepository.findById(quizId).get();
		QuizModel quizModel=getQuizToModel(quiz);
		List<BatchModel> batchModels=new ArrayList<>();
		List<Batch> quizBatches= batchRepository.findAllByQuiz(quiz);
//		if(quizBatches==null)
//			quizBatches= Collections.emptyList();

		quizBatches.forEach(batch -> {
			BatchModel batchModel =new BatchModel();
			BeanUtils.copyProperties(batch, batchModel);
			List<Long> batchUsers=new ArrayList<>();
			List<BatchUserMapping> batchMappings=batchUserMappingRepo.findByBatch(batch);
			batchMappings.forEach(batchMapping->{
				 		User user=new User();
				 		user=batchMapping.getUser();
				 		batchUsers.add(user.getId());
				 	});

				 	batchModel.setUserIds(batchUsers);
				 	batchModels.add(batchModel);

		});



		//List<QuizBatchMapping> quizMappings=quizBatchMappingRepo.findByQuiz(quiz);
		// quizMappings.forEach(quizMapping->{
		// 	Batch batch=new Batch();
		// 	batch=quizMapping.getBatch();
		// 	List<Long> batchUsers=new ArrayList<>();
		// 	List<BatchUserMapping> batchMappings=batchUserMappingRepo.findByBatch(batch);
		// 	BatchModel batchModel=new BatchModel();
		// 	BeanUtils.copyProperties(batch, batchModel);
		// 	batchMappings.forEach(batchMapping->{
		// 		User user=new User();
		// 		user=batchMapping.getUser();
		// 		batchUsers.add(user.getId());
		// 	});

		// 	batchModel.setUserIds(batchUsers);
		// 	batchModels.add(batchModel);
		// });
		quizModel.setBatches(batchModels);
    	String questionsString=quiz.getQuestionsOfQuiz();
    	Set<Question> questions = new HashSet<>();
    	String[] questionsArray=questionsString.split(",");
    	for(int i=0;i<questionsArray.length;i++) {
			String questId=questionsArray[i];
			if (!questId.isEmpty() && questionRepo.findById(Long.parseLong(questId)).isPresent())
        		questions.add(questionRepo.findById(Long.parseLong(questId)).get());
    	}
    	quizModel.setQuestionsOfQuiz(questions);/// chnage here for shuffling quiz
		return quizModel;
    }

    @Override
    public void deleteQuiz(Long quizId) throws Exception {

		
				batchService.deleteAllBatchesForQuiz(quizId);
					this.quizRepository.deleteById(quizId);   
                
        
    }

    private QuizModel getQuizToModel(Quiz quiz) {
    	QuizModel model=new QuizModel();
    	model.setQuizId(quiz.getQuizId());
    	model.setName(quiz.getName());
    	model.setNoOfQuestions(quiz.getNoOfQuestions());
    	model.setMarkOfQuestion(quiz.getMarkOfQuestion());
    	model.setDescription(quiz.getDescription());
    	model.setActive(quiz.isActive());
    	model.setQuizTime(quiz.getQuizTime());
    	model.setMaxMarks(quiz.getMaxMarks());
		model.setTrade(quiz.getTrade());
		model.setNegativeMarks(quiz.isNegativeMarks());
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		model.setCreatedAt(dateFormat.format(quiz.getCreatedAt()));
		model.setUpdatedAt(dateFormat.format(quiz.getUpdatedAt()));
    	//model.setBatches(quiz.getBatches());
    	
    	return model;
    }
    private Quiz getModelToQuiz(QuizModel quizModel) {
    	
    	Quiz quiz=new Quiz();
		quiz.setQuizId(quizModel.getQuizId());
		quiz.setName(quizModel.getName());
		quiz.setActive(quizModel.isActive());
		quiz.setDescription(quizModel.getDescription());
		quiz.setMarkOfQuestion(quizModel.getMarkOfQuestion());
		quiz.setMaxMarks(quizModel.getMaxMarks());
		quiz.setNoOfQuestions(quizModel.getNoOfQuestions());
		quiz.setQuizTime(quizModel.getQuizTime());
		quiz.setTrade(quizModel.getTrade());
		quiz.setNegativeMarks(quizModel.isNegativeMarks());
		//quiz.setBatches(quizModel.getBatches());
		
		return quiz; 	
    }



}
