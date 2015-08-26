(function($, window, document){

    "use strict"

    var Todo = Base.extend({

    });

    var TodoView = BaseView.extend({

        entityType: 'TODO',
        el: $('#todo-wrapper'),
        upsertTemplate: $('#todo-upsert-template').html(),
        displayTemplate: $('#todo-display-template').html(),

        events: {
            'click #todo-submit': 'upsertTodo',
            'click #todo-cancel': 'resetValues',
            'click #todo-tag-img': 'displayTagSelection',
            'click .add-task' : 'showTaskForm'
        },

        showTaskForm: function(e) {

            e.preventDefault();
            this.$el.find('.add-task').addClass('invisible').fadeOut();
            this.$el.find('#task-form').removeClass('invisible').fadeIn();
        },

        getModel: function() {
            return new Todo();
        },

        buildModel: function(entity) {

        },

        prepareVariables: function() {

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

        }

    });

    window.todoView = new TodoView();

})(jQuery, window, document);