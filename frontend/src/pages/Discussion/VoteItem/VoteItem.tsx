import Loading from '@/components/common/Loading/Loading';

import { convertIdxToVoteColorKey } from '@/utils/converter';
import usePostVoteItem from '@/pages/Discussion/hooks/usePostVoteItem';

import * as S from '@/pages/Discussion/VoteItem/VoteItem.styles';

export interface VoteItemProps {
	title: string;
	itemVotes: number;
	totalVotes: number;
	idx: number;
	name: string;
}

const VoteItem = ({ title, itemVotes, totalVotes, idx, name }: VoteItemProps) => {
	const progressivePercent = Math.floor((itemVotes / totalVotes) * 100);
	const { onChangeRadio, isLoading } = usePostVoteItem();

	if (isLoading) return <Loading />;

	return (
		<S.Container>
			<S.TitleBox>
				<S.RadioButton
					type="radio"
					name={name}
					onChange={() => {
						onChangeRadio(name, idx);
					}}
				/>
				<S.Title>
					<p>{title}</p>
					<S.ItemVotes>{`(${itemVotes}표)`}</S.ItemVotes>
				</S.Title>
			</S.TitleBox>

			<S.ProgressiveBar>
				<S.ProgressiveBarContent
					percent={progressivePercent || 0}
					colorKey={convertIdxToVoteColorKey(idx)}
				/>
			</S.ProgressiveBar>
		</S.Container>
	);
};

export default VoteItem;
