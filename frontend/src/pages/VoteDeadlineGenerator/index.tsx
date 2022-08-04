import { AxiosError, AxiosResponse } from 'axios';
import React, { useEffect, useRef } from 'react';
import { useMutation } from 'react-query';
import { useNavigate } from 'react-router-dom';

import { registerVoteItems } from '@/api/vote';
import Loading from '@/components/common/Loading/Loading';
import useLocationState from '@/hooks/useLocationState';
import * as S from '@/pages/VoteDeadlineGenerator/index.styles';

const VoteDeadlineGenerator = () => {
	const dateRef = useRef<HTMLInputElement>(null);
	const timeRef = useRef<HTMLInputElement>(null);
	const { articleId, items } = useLocationState<{ articleId: string; items: string[] }>();
	const navigate = useNavigate();
	const { isLoading, mutate, isError, data, isSuccess } = useMutation<
		AxiosResponse<{ articleId: string }>,
		AxiosError<{ errorCode: string; message: string }>,
		{ articleId: string; items: string[]; expiryDate: string }
	>(registerVoteItems);

	useEffect(() => {
		if (isSuccess) {
			alert('등록이 완료되었습니다.');
			navigate(`/articles/discussion/${articleId}`);
		}
	}, [isSuccess]);

	const handleSubmitVoteDeadlineForm = (e: React.FormEvent<HTMLFormElement>) => {
		e.preventDefault();

		mutate({
			articleId,
			items,
			expiryDate: `${dateRef.current?.value}T${timeRef.current?.value}`,
		});
	};

	if (isLoading) return <Loading />;

	return (
		<S.Container onSubmit={handleSubmitVoteDeadlineForm}>
			<S.VoteDeadlineLabel>마감 기한을 설정해주세요.</S.VoteDeadlineLabel>
			<S.VoteDeadlineInputBox>
				<S.DeadlineInput type="date" required min="2022-08-02" ref={dateRef} />
				<S.ValidateMessage></S.ValidateMessage>
				<br />
				<S.DeadlineInput type="time" required ref={timeRef} />
				<S.ValidateMessage></S.ValidateMessage>
			</S.VoteDeadlineInputBox>
			<S.SubmitButton>등록하기</S.SubmitButton>
		</S.Container>
	);
};

export default VoteDeadlineGenerator;
