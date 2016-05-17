package br.com.fatec.netty.chat.example.view.utils;

import br.com.fatec.netty.chat.example.domain.UserChannel;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

public class DefaultListCell extends ListCell<UserChannel> {

	@Override
	protected void updateItem(UserChannel item, boolean empty) {
		super.updateItem(item, empty);

		if (empty) {
			setText(null);
			setGraphic(null);

		} else if (item != null) {
			setText(null);

			VBox root = new VBox(3);
			root.setPrefHeight(48);
			root.setAlignment(Pos.CENTER_LEFT);

			Label labelName = new Label();
			labelName.setText("Nome::: " + item.getName());

			Label labelAddress = new Label();
			labelAddress.setText("Endereço: " + item.getAddress());

			Label labelId = new Label();
			labelId.setText("Id::: " + item.getId());

			root.getChildren().addAll(labelName, labelId, labelAddress);

			setGraphic(root);

		} else {
			setText(item == null ? "null" : item.toString());
			setGraphic(null);
		}

	}

}
