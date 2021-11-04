package com.company.app.screen.topic;

import com.company.app.entity.Comment;
import com.company.app.entity.Topic;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.DataGrid;
import io.jmix.ui.model.CollectionPropertyContainer;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Topic.edit")
@UiDescriptor("topic-edit.xml")
@EditedEntityContainer("topicDc")
public class TopicEdit extends StandardEditor<Topic> {

    @Autowired
    private DataContext dataContext;
    @Autowired
    private CollectionPropertyContainer<Comment> commentsDc;
    @Autowired
    private DataGrid<Comment> commentsTable;

    @Subscribe("commentsTable.create")
    public void onCommentsTableCreate(Action.ActionPerformedEvent event) {
        Comment comment = dataContext.create(Comment.class);
        comment.setTopic(getEditedEntity());
        commentsDc.getMutableItems().add(comment);
        commentsTable.edit(comment);
    }

    @Subscribe("commentsTable.edit")
    public void onCommentsTableEdit(Action.ActionPerformedEvent event) {
        Comment comment = commentsTable.getSingleSelected();
        if (comment != null) {
            commentsTable.edit(comment);
        }
    }
}