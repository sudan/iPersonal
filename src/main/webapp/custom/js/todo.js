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
            this.saveForm = $('#todo-form');
            this.tasksDiv =  $('#tasks');
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

            this.model = new Task({
                name : this.$el.find('[name=name]').val(),
                task : this.$el.find('[name=task]').val(),
                priority: this.$el.find('[name=priority]:checked').val().toLowerCase(),
                percentCompletion: this.$el.find('[name=percent-completion]').val()
            });

            var errors = this.model.validate(this.model.attributes);
            if (errors) {
                this.renderErrors(errors);
                return;
            }

            this.$el.find('#task-form').addClass('invisible').fadeOut();
            this.$el.find('.add-task').removeClass('invisible').fadeIn();

            //var template = _.template(this.taskTemplate);
            //this.tasks.append(template(this.model.toJSON()));

            this.$el.find('[name=name]').val('');
            this.$el.find('[name=task]').val('');
            this.$el.find('[name=percent-completion]').val(0);
        },

        cancelTask: function(e) {
            e.preventDefault();

            this.$el.find('[name=name]').val('');
            this.$el.find('[name=task]').val('');
            this.$el.find('[name=percent-completion]').val(0);
            this.$el.find('#task-form').addClass('invisible').fadeOut();
            this.$el.find('.add-task').removeClass('invisible').fadeIn();
        },

        editTask: function(e) {

            var taskDiv = $(e.target).closest('.task-entity');
            var name = taskDiv.find('.name').html();
            var task = taskDiv.find('.task').html().trim();
            var priority = taskDiv.find('.priority').html();
            var percentCompletion = taskDiv.find('.percentCompletion').html().split(' ');

            this.$el.find('[name=name]').val(name);
            this.$el.find('[name=task]').val(task);
            this.$el.find('[name=percent-completion]').val(percentCompletion);
            this.$el.find('[name=priority]').removeAttr('checked');
            this.$el.find('#' + priority.toLowerCase()).click();

            this.showTaskForm();
        },

        deleteTask: function(e) {

            $(e.target).closest('div.task-entity').remove();
        }

    });

    window.todoView = new TodoView();

})(jQuery, window, document);