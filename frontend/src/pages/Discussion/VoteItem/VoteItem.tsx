import * as S from './VoteItem.styles';
import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useMutation } from 'react-query';

import { checkVoteItems } from '@/api/vote';
import Loading from '@/components/common/Loading/Loading';
import { queryClient } from '@/index';
import { convertIdxToVoteColorKey } from '@/utils/converter';

export interface VoteItemProps {
	title: string;
	itemVotes: number;
	totalVotes: number;
	idx: number;
	name: string;
}

const VoteItem = ({ title, itemVotes, totalVotes, idx, name }: VoteItemProps) => {
	const progressivePercent = Math.floor((itemVotes / totalVotes) * 100);
	const { isLoading, isError, mutate, isSuccess } = useMutation<
		unknown,
		AxiosError,
		{ articleId: string; voteId: string }
	>(checkVoteItems);

	useEffect(() => {
		if (isSuccess) {
			queryClient.refetchQueries('vote');
		}
	}, [isSuccess]);

	if (isLoading) return <Loading />;

	if (isError) return <div>에러..!</div>;

	const onChangeRadio = () => {
		mutate({ articleId: name, voteId: String(idx) });
	};

	return (
		<S.Container>
			<S.TitleBox>
				<S.RadioButton type="radio" name={name} onChange={onChangeRadio} />
				<S.Title>
					<p>{title}</p>
					<S.ItemVotes>{`(${itemVotes}표)`}</S.ItemVotes>
				</S.Title>
			</S.TitleBox>

			<S.ProgressiveBar>
				<S.ProgressiveBarContent
					percent={progressivePercent || 0}
					colorKey={convertIdxToColorKey(idx)}
				/>
			</S.ProgressiveBar>
		</S.Container>
	);
};

export default VoteItem;
