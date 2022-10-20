import ProgressiveBar from '@/components/@common/ProgressiveBar/ProgressiveBar';
import * as S from '@/components/vote/VoteItem/VoteItem.styles';
import usePostVoteItem from '@/hooks/vote/usePostVoteItem';
import { theme } from '@/styles/Theme';
import { convertIdxToVoteColorKey } from '@/utils/converter';

const VOTE_ITEM_PROGRESSIVE_TIEM = 0.3;

export interface VoteItemProps {
	voteItemId: number;
	title: string;
	itemVotes: number;
	totalVotes: number;
	colorIdx: number;
	articleId: string;
	expired: boolean;
	isVoted: boolean;
}

const VoteItem = ({
	voteItemId,
	title,
	itemVotes,
	totalVotes,
	colorIdx,
	articleId,
	expired,
	isVoted,
}: VoteItemProps) => {
	const progressivePercent = Math.floor((itemVotes / totalVotes) * 100) || 0;
	const { handleChangeVoteSelectButton } = usePostVoteItem(articleId);
	const gradientColor = theme.voteGradientColors[convertIdxToVoteColorKey(colorIdx)];

	return (
		<S.Container>
			<S.TitleBox>
				<S.RadioButton
					type="radio"
					name={articleId}
					onChange={() => {
						handleChangeVoteSelectButton(articleId, voteItemId);
					}}
					disabled={expired}
					checked={isVoted}
				/>
				<S.Title isVoted={isVoted} expired={expired}>
					<S.VoteName>{title}</S.VoteName>
					<S.ItemVotes>{`(${itemVotes}표)`}</S.ItemVotes>
				</S.Title>
			</S.TitleBox>

			<ProgressiveBar
				percent={progressivePercent}
				gradientColor={gradientColor}
				time={VOTE_ITEM_PROGRESSIVE_TIEM}
				width={theme.size.SIZE_170}
				height={theme.size.SIZE_010}
			/>
		</S.Container>
	);
};

export default VoteItem;
