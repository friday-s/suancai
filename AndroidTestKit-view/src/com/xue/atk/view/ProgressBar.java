package com.xue.atk.view;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ProgressBar extends JLabel {

	private boolean running = false;

	private ArrayList<ImageIcon> progressImages;

	private Thread mProgressThread;

	public ArrayList<ImageIcon> getProgressImages() {
		return progressImages;
	}

	public void setProgressImages(ArrayList<ImageIcon> progressImages) {
		this.progressImages = progressImages;
		this.setSize(progressImages.get(0).getIconWidth(), progressImages.get(0).getIconHeight());
	}

	public ProgressBar() {
		progressImages = new ArrayList<ImageIcon>();
	}

	public boolean getRunningState() {
		return running;
	}

	public void start() {
		running = true;
		mProgressThread = new Thread(progress);
		mProgressThread.start();
		this.setVisible(true);
	}

	public void stop() {
		if (running) {
			running = false;
		}
		mProgressThread = null;
		this.setVisible(false);
	}

	Runnable progress = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub

			while (running) {
				int index = 0;
				while (running && index < progressImages.size()) {

					setIcon(progressImages.get(index));
					index++;

					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	};
}
