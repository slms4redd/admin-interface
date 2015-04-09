import Ember from 'ember';

export default Ember.View.extend({
  didInsertElement : function () {
    this._super();
    Ember.run.scheduleOnce('afterRender', this, function () {
      this.$('.treeView').jstree({
        "core" : {
          "animation": 0,
          "multiple": false,
          "themes": {
            "dots": false,
            "variant": "large"
          },
          "check_callback": true
        },
        "types" : {
          "default" : {
            "icon" : "glyphicon glyphicon-file"
          },
          "group" : {
            "icon" : "glyphicon glyphicon-folder-open"
          }
        },
        "dnd": {
          "drop_target": ".drop-target",
          "check_while_dragging": false,
          "inside_pos": "last"
        },
        "plugins": ["dnd", "types", "themes"]
      });
    });
  }
});
