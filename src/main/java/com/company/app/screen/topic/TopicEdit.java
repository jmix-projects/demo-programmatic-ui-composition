package com.company.app.screen.topic;

import com.company.app.entity.Topic;
import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("Topic.edit")
@UiDescriptor("topic-edit.xml")
@EditedEntityContainer("topicDc")
public class TopicEdit extends StandardEditor<Topic> {
}