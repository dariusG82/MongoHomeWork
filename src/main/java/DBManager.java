import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.util.ArrayList;

public class DBManager {

    MongoClient mongoClient = new MongoClient();

    public void addStudentToDatabase(Student student) {
        MongoCollection<Document> collection = getMongoCollection();
        collection.insertOne(new Document("id", student.id()).append("vardas", student.name()).append("pavarde", student.surname()));

    }

    public Document getStudentFromDatabase(String vardas, String pavarde) {
        MongoCollection<Document> collection = getMongoCollection();
        for (Document document : collection.find()) {
            if (document.get("vardas").equals(vardas) && document.get("pavarde").equals(pavarde)) {
                return document;
            }
        }
        return null;
    }

    public void addExamScoreToDatabase(Document document, String exam, Integer score) {
        MongoCollection<Document> collection = getMongoCollection();
        collection.updateOne(document, Updates.set(exam, score));
    }

    public ArrayList<ExamGrade> getStudentGradesByExam(String exam) {
        MongoCollection<Document> collection = getMongoCollection();

        ArrayList<ExamGrade> grades = new ArrayList<>();

        for (Document document : collection.find()) {
            if (document.get(exam) != null) {
                grades.add(new ExamGrade(
                        document.getString("vardas"),
                        document.getString("pavarde"),
                        document.getInteger(exam)));
            }
        }

        return grades;
    }

    private MongoCollection<Document> getMongoCollection() {
        String DATABASE = "JavaSchool";
        MongoDatabase database = mongoClient.getDatabase(DATABASE);
        String COLLECTION_STUDENTS = "Students";
        return database.getCollection(COLLECTION_STUDENTS);
    }
}
