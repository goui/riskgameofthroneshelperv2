package fr.goui.riskgameofthroneshelperv2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.goui.riskgameofthroneshelperv2.R;
import fr.goui.riskgameofthroneshelperv2.model.Player;
import fr.goui.riskgameofthroneshelperv2.model.Territory;

/**
 * Adapter for the list of territories.
 */
public class TerritoryAdapter extends RecyclerView.Adapter<TerritoryAdapter.TerritoryViewHolder> {

    private LayoutInflater mLayoutInflater;

    private Player mPlayer;

    public TerritoryAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public TerritoryAdapter.TerritoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TerritoryViewHolder(mLayoutInflater.inflate(R.layout.item_territory, parent, false));
    }

    @Override
    public void onBindViewHolder(TerritoryAdapter.TerritoryViewHolder holder, int position) {
        final Territory territory = mPlayer.getTerritories().get(position);
        if (territory != null) {
            holder.nameTextView.setText(territory.getName());
            holder.discardButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPlayer.getTerritories().remove(territory);
                    notifyDataSetChanged();
                }
            });
        }
    }

    public void setPlayer(Player player) {
        mPlayer = player;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mPlayer.getTerritories().size();
    }

    static class TerritoryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_territory_discard)
        ImageView discardButton;

        @BindView(R.id.territory_name_text_view)
        TextView nameTextView;

        public TerritoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
