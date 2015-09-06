(function($, window, document){

    "use strict"

    var Todo = Base.extend({

    });

    var Task = Base.extend({
        initialize: function() {
            this.mandatory = {
                'name': true,
                'task': true
            },
            this.maxLength = {
                'name': 50,
                'task': 300
            },
            this.formAttributes = ['name', 'task'];
        }
    });

    var Tasks = Backbone.Collection.extend({
        model: Task
    });

    var TodoView = BaseView.extend({

        entityType: 'TODO',
        el: $('#todo-wrapper'),
        upsertTemplate: $('#todo-upsert-template').html(),
        displayTemplate: $('#todo-display-template').html(),
        taskTemplate: $('#task-display-template').html(),

        events: {
            'click #todo-submit': 'upsertTodo',
            'click #todo-cancel': 'resetValues',
            'click #todo-tag-img': 'displayTagSelection',
            'click .add-task' : 'showTaskForm',
            'click #task-add-button': 'addTask',
            'click #task-cancel-button': 'cancelTask',
            'click .delete': 'deleteTask',
            'click .edit': 'editTask'
        },

        showTaskForm: function(e) {

            if (e) {
                e.preventDefault();
            }
            this.$el.find('.add-task').addClass('invisible').fadeOut();
            this.$el.find('#task-form').removeClass('invisible').fadeIn();
        },

        getModel: function() {
            return new Todo();
        },

        buildModel: function(entity) {

        },

        prepareVariables: function() {
            this.collection = new Tasks();
            this.saveForm = $('#todo-form');
            this.tasksDiv =  $('#tasks');

            this.saveForm.find('[name=percent-completion]').on('change', this.showPercentTitle);
        },

        showPercentTitle: function(e) {
            $(e.target).parent('.form-group').find('.percent-completion-label').html('Percent Completion: '
                + $(e.target).val() + "%");
        },

        initializeUpdateForm: function() {

        },

        upsertTodo: function(e) {

        },

        fetchTodos: function(e) {

        },

        buildEntityList: function() {

        },

        getDeletableModel: function(id) {

        },

        addTask: function(e) {
            e.preventDefault();

            var taskId = this.$el.find('.taskId').html();

            if (!taskId){
                this.model = new Task({
                    name : this.$el.find('[name=name]').val(),
                    task : this.$el.find('[name=task]').val(),
                    priority: this.$el.find('[name=priority]:checked').val().toLowerCase(),
                    percentCompletion: this.$el.find('[name=percent-completion]').val()
                });
            } else {
                this.model = this.collection.get(taskId);
                this.model.set({
                    name : this.$el.find('[name=name]').val(),
                    task : this.$el.find('[name=task]').val(),
                    priority: this.$el.find('[name=priority]:checked').val().toLowerCase(),
                    percentCompletion: this.$el.find('[name=percent-completion]').val()
                });
            }

            var errors = this.model.validate(this.model.attributes);
            if (errors) {
                this.renderErrors(errors);
                return;
            }

            this.resetTask();
            this.collection.add(this.model);
            this.renderTasks();
        },

        cancelTask: function(e) {
            e.preventDefault();

            this.resetTask();
        },

        resetTask: function() {

            this.$el.find('#task-form').addClass('invisible').fadeOut();
            this.$el.find('.add-task').removeClass('invisible').fadeIn();
            this.$el.find('.taskId').empty();
            this.$el.find('[name=name]').val('').removeClass('error-field');
            this.$el.find('.name-error').html('');
            this.$el.find('[name=task]').val('').removeClass('error-field');
            this.$el.find('.task-error').html('');
            this.$el.find('[name=percent-completion]').val(0);
        },

        editTask: function(e) {

            var taskDiv = $(e.target).closest('.task-entity');
            var name = taskDiv.find('.name').html();
            var task = taskDiv.find('.task').html().trim();
            var priority = taskDiv.find('.priority').html();
            var taskId = taskDiv.find('.id').html();
            var percentCompletion = taskDiv.find('.percentCompletion').html().split(' ');

            this.$el.find('.taskId').html(taskId);
            this.$el.find('[name=name]').val(name);
            this.$el.find('[name=task]').val(task);
            this.$el.find('[name=percent-completion]').val(percentCompletion);
            this.$el.find('[name=priority]').removeAttr('checked');
            this.$el.find('#' + priority.toLowerCase()).click();

            this.showTaskForm();
        },

        deleteTask: function(e) {

            var taskId = $(e.target).closest('div.task-entity').find('.id').html();
            this.collection.remove(this.collection.get(taskId));
            this.renderTasks();
        },

        buildTaskList: function() {

            var entityList = [];

            for (var i = 0; i < this.collection.length; i++) {

                var entity = {
                    'id': this.collection.models[i].cid,
                    'priority': this.collection.models[i].attributes.priority,
                    'percentCompletion': this.collection.models[i].attributes.percentCompletion,
                    'name': this.collection.models[i].attributes.name,
                    'task': this.collection.models[i].attributes.task
                };
                entityList.push(entity);
            }
            return entityList;
        },

        renderTasks: function() {

            this.tasksDiv.empty();
            var taskEntities = this.buildTaskList();
            var template = _.template(this.taskTemplate);

            this.tasksDiv.html(
                template({
                    'tasks': taskEntities
                }
            ));
        }

    });

    window.todoView = new TodoView();

})(jQuery, window, document);