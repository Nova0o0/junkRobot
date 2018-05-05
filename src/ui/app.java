package ui;

import java.util.List;
import java.util.Vector;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import kernel.Action;
import kernel.Element;
import kernel.Element.ElementListener;
import main.World;

public class app extends Application {

	private class MyTextArea extends TextArea implements ElementListener {

		@Override
		public void onUpdate(Element element) {
			String currentElement = element.print();
			
			String pendingActions = "";
			for (Action pendingAction : element.getPendingActions()) {
				pendingActions += "\n" + pendingAction.name();
			}

			setText(currentElement+pendingActions);
		}
	}
	
	private World m_world;
	private List<DisplayedElement> m_elements;
	
	@Override
	public void start(Stage stage) throws Exception {

		m_world = new World();
		m_elements = new Vector<>();
		
		int width = World.CELL_SIZE * World.WIDTH;
		int height = World.CELL_SIZE * World.HEIGHT;
		
		Pane group = new Pane();
		group.setPrefSize(width,height);
		for (int i=0 ; i<1000 ; i+=50) {
			Line line = new Line(i, 0, i, 800);
			group.getChildren().add(line);
		}
		
		for (int i=0 ; i<800 ; i+=50) {
			Line line = new Line(0, i, 1000, i);
			group.getChildren().add(line);
		}
        
        VBox buttons = new VBox();
        for(Action action : Action.values()) {
        	buttons.getChildren().add(action.createButton(m_world));
        }
        
        MyTextArea textArea = new MyTextArea();
        textArea.setPrefSize(400, 200);
        buttons.getChildren().add(textArea);
        m_world.getPlayer().addListener(textArea);

        BorderPane pane = new BorderPane();
		pane.setRight(buttons);
        pane.setCenter(group);
		Scene scene = new Scene(pane, width + 400, height);
		
		for(Element element: m_world.getElements()) {
			DisplayedElement displayedElement = new DisplayedElement(group);
			element.addListener(displayedElement);
			m_elements.add(displayedElement);
		}
		
		m_world.startGame();

		stage.setTitle("JunkyRobot");
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void stop() throws Exception {
		super.stop();
		
		m_world.stopGame();
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}
