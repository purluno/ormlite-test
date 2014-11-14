package purluno.ormlitetest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;

public class MainActivity extends Activity {
    private DatabaseOpenHelper databaseOpenHelper;

    private Dao<Person, Long> personDao;

    private EditText nameEdit;

    private EditText ageEdit;

    private EditText genderEdit;

    private TextView outputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            databaseOpenHelper = new DatabaseOpenHelper(this);
            personDao = DaoManager.createDao(databaseOpenHelper.getConnectionSource(), Person.class);
            setContentView(R.layout.activity_main);
            nameEdit = (EditText) findViewById(R.id.nameEdit);
            ageEdit = (EditText) findViewById(R.id.ageEdit);
            genderEdit = (EditText) findViewById(R.id.genderEdit);
            outputText = (TextView) findViewById(R.id.outputText);
            super.onCreate(savedInstanceState);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onDestroy() {
        databaseOpenHelper.close();
        super.onDestroy();
    }

    public void onInsertButtonClick(View view) {
        try {
            Person person = new Person();
            person.setName(getName());
            person.setAge(getAge());
            person.setGender(getGender());
            int numRows = personDao.create(person);
            outputText.setText("삽입 성공 (" + numRows + ")");
        } catch (SQLException e) {
            Log.w("MainActivity", e);
            outputText.setText("삽입: " + e.getMessage());
        }
    }

    public void onDeleteButtonClick(View view) {
        try {
            DeleteBuilder<Person, Long> deleteBuilder = personDao.deleteBuilder();
            Where where = deleteBuilder.where();
            where.eq("name", getName());
            int numRows = personDao.delete(deleteBuilder.prepare());
            outputText.setText("삭제: " + numRows + " 건");
        } catch (SQLException e) {
            Log.w("MainActivity", e);
            outputText.setText("삭제: " + e.getMessage());
        }
    }

    public void onListButtonClick(View view) {
        StringBuilder sb = new StringBuilder("목록\n");
        for (Person p : personDao) {
            sb.append(p.getId());
            sb.append(" / ");
            sb.append(p.getName());
            sb.append(" / ");
            sb.append(p.getAge());
            sb.append(" / ");
            sb.append(p.getGender());
            sb.append("\n");
        }
        outputText.setText(sb.toString());
    }

    public String getName() {
        return nameEdit.getText().toString();
    }

    public Integer getAge() {
        String ageString = ageEdit.getText().toString();
        Integer age;
        if (ageString.isEmpty()) {
            return null;
        } else {
            return Integer.parseInt(ageString);
        }
    }

    public String getGender() {
        return genderEdit.getText().toString();
    }
}
