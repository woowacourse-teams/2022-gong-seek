import usePostVoteItem from '@/hooks/vote/usePostVoteItem';
import * as S from '@/pages/Discussion/VoteItem/VoteItem.styles';
import { convertIdxToVoteColorKey } from '@/utils/converter';

export interface VoteItemProps {
	voteItemId: number;
	title: string;
	itemVotes: number;
	totalVotes: number;
	colorIdx: number;
	articleId: string;
	isExpired: boolean;
	isVoted: boolean;
}

const VoteItem = ({
	voteItemId,
	title,
	itemVotes,
	totalVotes,
	colorIdx,
	articleId,
	isExpired,
	isVoted,
}: VoteItemProps) => {
	//TODO: too many props 컴포넌트의 분리 필요,
	const progressivePercent = Math.floor((itemVotes / totalVotes) * 100);
	const { onChangeRadio } = usePostVoteItem(articleId);

	return (
		<S.Container>
			<S.TitleBox>
				<S.RadioButton
					type="radio"
					name={articleId}
					onChange={() => {
						onChangeRadio(articleId, voteItemId);
					}}
					disabled={isExpired}
					checked={isVoted}
				/>
				<S.Title isVoted={isVoted}>
					<p>{title}</p>
					<S.ItemVotes>{`(${itemVotes}표)`}</S.ItemVotes>
				</S.Title>
			</S.TitleBox>

			<S.ProgressiveBar>
				<S.ProgressiveBarContent
					percent={progressivePercent || 0}
					colorKey={convertIdxToVoteColorKey(colorIdx)}
				/>
			</S.ProgressiveBar>
		</S.Container>
	);
};

export default VoteItem;
