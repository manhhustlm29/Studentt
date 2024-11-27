package com.example.studentmanager

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.studentmanager.adapter.StudentAdapter
import com.example.studentmanager.data.students
import com.example.studentmanager.model.Student

class MainActivity : AppCompatActivity() {
    private lateinit var studentList: MutableList<Student>
    private lateinit var adapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        studentList = students

        val listView = findViewById<ListView>(R.id.lvStudents)
        adapter = StudentAdapter(this, R.layout.item_student, studentList)
        listView.adapter = adapter

        registerForContextMenu(listView)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val position = info.position

        when (item.itemId) {
            R.id.action_edit -> {
                val intent = Intent(this, StudentActivity::class.java).apply {
                    putExtra("id", students[position].id.toString())
                    putExtra("name", students[position].name)
                }
                startActivityForResult(intent, REQUEST_EDIT)
                return true
            }
            R.id.action_remove -> {
                studentList.removeAt(position)
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "Student removed", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_new) {
            val intent = Intent(this, StudentActivity::class.java)
            startActivityForResult(intent, REQUEST_ADD)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            val id = data.getStringExtra("id") ?: return
            val name = data.getStringExtra("name") ?: return

            val student = Student(name,id.toInt())
            when (requestCode) {
                REQUEST_ADD -> {
                    students.add(student)
                    adapter.notifyDataSetChanged()
                }
                REQUEST_EDIT -> {
                    val position = students.indexOfFirst { it.id == id.toInt() }
                    if (position != -1) {
                        students[position] = student
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }


    companion object {
        const val REQUEST_ADD = 1
        const val REQUEST_EDIT = 2
    }
}