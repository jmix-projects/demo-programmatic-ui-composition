package com.company.app.screen.topic;

import com.company.app.entity.Comment;
import com.company.app.entity.Topic;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.*;
import io.jmix.ui.model.CollectionPropertyContainer;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@UiController("Topic.edit")
@UiDescriptor("topic-edit.xml")
@EditedEntityContainer("topicDc")
public class TopicEdit extends StandardEditor<Topic> {

    @Autowired
    private DataContext dataContext;
    @Autowired
    private CollectionPropertyContainer<Comment> commentsDc;
    @Autowired
    private HBoxLayout newCommentBox;
    @Autowired
    private Button addCommentBtn;
    @Autowired
    private TextField<String> newCommentEditField;
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private GroupBoxLayout commentsBox;

    private Comment addedComment;

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        refreshCommentsView();
    }

    private void refreshCommentsView() {
        Collection<Component> components = commentsBox.getComponents();
        for (Component component : components) {
            if (component != newCommentBox && component != addCommentBtn) {
                commentsBox.remove(component);
            }
        }

        int idx = 0;
        for (Comment comment : commentsDc.getItems()) {
            commentsBox.add(createCommentPanel(comment), idx++);
        }
    }

    private Component createCommentPanel(Comment comment) {
        HBoxLayout commentBox = uiComponents.create(HBoxLayout.class);
        commentBox.setWidth("100%");
        commentBox.setSpacing(true);

        TextField<String> commentField = uiComponents.create(TextField.TYPE_STRING);
        commentField.setValue(comment.getText());
        commentField.setEditable(false);

        Button removeBtn = uiComponents.create(Button.class);
        removeBtn.setCaption("Remove");
        removeBtn.addClickListener(clickEvent -> {
            commentsDc.getMutableItems().remove(comment);
            dataContext.remove(comment);
            commentsBox.remove(commentBox);
        });

        commentBox.add(commentField);
        commentBox.add(removeBtn);
        commentBox.expand(commentField);

        return commentBox;
    }


    @Subscribe("addCommentBtn")
    public void onAddCommentBtnClick(Button.ClickEvent event) {
        addCommentBtn.setVisible(false);
        newCommentBox.setVisible(true);

        Comment comment = dataContext.create(Comment.class);
        comment.setTopic(getEditedEntity());
        commentsDc.getMutableItems().add(comment);
        this.addedComment = comment;
    }

    @Subscribe("newCommentOkBtn")
    public void onNewCommentOkBtnClick(Button.ClickEvent event) {
        addCommentBtn.setVisible(true);
        newCommentBox.setVisible(false);

        addedComment.setText(newCommentEditField.getValue());
        addedComment = null;
        newCommentEditField.setValue(null);

        refreshCommentsView();
    }

    @Subscribe("newCommentCancelBtn")
    public void onNewCommentCancelBtnClick(Button.ClickEvent event) {
        addCommentBtn.setVisible(true);
        newCommentBox.setVisible(false);

        if (addedComment != null) {
            commentsDc.getMutableItems().remove(addedComment);
            dataContext.remove(addedComment);
            addedComment = null;
            newCommentEditField.setValue(null);
        }
    }
}