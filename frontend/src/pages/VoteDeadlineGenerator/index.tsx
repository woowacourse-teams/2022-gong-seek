import { AxiosError, AxiosResponse } from 'axios';
import React, { useEffect, useRef } from 'react';
import { useMutation } from 'react-query';
import { useNavigate } from 'react-router-dom';

import { registerVoteItems } from '@/api/vote';
import Loading from '@/components/common/Loading/Loading';
import CustomError from '@/components/helper/CustomError';
import { ErrorMessage } from '@/constants/ErrorMessage';
import useLocationState from '@/hooks/useLocationState';
import * as S from '@/pages/VoteDeadlineGenerator/index.styles';
import { afterWeekGenerator, tomorrowGenerator } from '@/utils/dateGenerator';

const VoteDeadlineGenerator = () => {
	const dateRef = useRef<HTMLInputElement>(null);
	const timeRef = useRef<HTMLInputElement>(null);
	const { articleId, items } = useLocationState<{ articleId: string; items: string[] }>();
	const navigate = useNavigate();
	const { isLoading, mutate, isError, error, isSuccess } = useMutation<
		AxiosResponse<{ articleId: string }>,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>,
		{ articleId: string; items: string[]; expiryDate: string }
	>(registerVoteItems);
	const tomorrow = tomorrowGenerator();
	const afterWeek = afterWeekGenerator();

	useEffect(() => {
		if (isSuccess) {
			alert('등록이 완료되었습니다.');
			navigate(`/articles/discussion/${articleId}`);
		}
	}, [isSuccess]);

	useEffect(() => {
		if (isError) {
			if (!error.response) {
				return;
			}
			throw new CustomError(
				error.response.data.errorCode,
				ErrorMessage[error.response.data.errorCode],
			);
		}
	}, [isError]);

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
				<S.DeadlineInput type="date" required min={tomorrow} max={afterWeek} ref={dateRef} />
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
