package com.example.todolist

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import kotlinx.android.synthetic.main.item_todo.view.*

class ToDoAdapter(
    private val toDos: MutableList<ToDo>,

): RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {
    class ToDoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        return ToDoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_todo,
                parent,
                false
            )
        )
    }
    private fun toggleStrikeThrough(tvTodoTitle : TextView, isChecked: Boolean){
        if(isChecked){
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        }
        else{
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    fun addToDo(todo : ToDo){
        toDos.add(todo)
        notifyItemInserted(toDos.size-1)
    }

    fun deleteDoneToDos(){
        toDos.removeAll { todo ->
            todo.isChecked
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        val currToDo = toDos[position]
        holder.itemView.apply {
            tvTodoTitle.text = currToDo.title
            cbDone.isChecked = currToDo.isChecked
            toggleStrikeThrough(tvTodoTitle, currToDo.isChecked)
            cbDone.setOnCheckedChangeListener { _, isChecked ->
                toggleStrikeThrough(tvTodoTitle, isChecked)
                currToDo.isChecked = !currToDo.isChecked
            }
        }
    }

    override fun getItemCount(): Int {
        return toDos.size
    }
}