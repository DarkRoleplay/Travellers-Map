package net.dark_roleplay.travellers_map.screens.screens.widgets;

import com.google.common.collect.Lists;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;

import java.util.List;

public class PagedWidget extends NestedWidget {

	protected final List<List<IGuiEventListener>> listenerStack = Lists.newArrayList();
	protected final List<List<IRenderable>>  rendererStack = Lists.newArrayList();

	protected int currentPage = -1;

	public PagedWidget(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	public void finishPage(){
		this.listenerStack.add(this.listeners);
		this.listeners = Lists.newArrayList();
		this.rendererStack.add(this.renderers);
		this.renderers = Lists.newArrayList();
		this.currentPage ++;
	}

	public void nextPage(){
		if(this.currentPage +1 < this.listenerStack.size()){
			this.currentPage ++;
		}else{
			this.currentPage = 0;
		}
		this.listeners = this.listenerStack.get(this.currentPage);
		this.renderers = this.rendererStack.get(this.currentPage);
	}

	public void prevPage(){
		if(this.currentPage -1 >= 0){
			this.currentPage --;
		}else{
			this.currentPage = this.listenerStack.size() - 1;
		}
		this.listeners = this.listenerStack.get(this.currentPage);
		this.renderers = this.rendererStack.get(this.currentPage);
	}
}
