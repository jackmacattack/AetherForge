package edu.virginia.cs.sgd;

import android.os.AsyncTask;
import edu.virginia.cs.sgd.util.Executor;

public class AndroidExecutor implements Executor {
	
	private boolean end;
	
	@Override
	public void execute(Runnable r) {
		end = false;
		AndroidThread.execute(r);

	}

	@Override
	public boolean isExecuting() {
		return !end;
	}

	private class AndroidThread extends AsyncTask<Runnable, Boolean, String> {

		@Override
		protected String doInBackground(Runnable... r) {
			r[0].run();
			return null;
		}
		
		@Override
		protected void onPostExecute(String res) {
			end = true;
		}
	}
}
