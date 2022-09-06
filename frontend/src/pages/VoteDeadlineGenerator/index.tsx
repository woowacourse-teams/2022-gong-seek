import { AxiosError, AxiosResponse } from 'axios';
import React, { useEffect, useState } from 'react';
import { useMutation } from 'react-query';
import { useNavigate } from 'react-router-dom';

import { registerVoteItems } from '@/api/vote';
import Loading from '@/components/common/Loading/Loading';
import CustomError from '@/components/helper/CustomError';
import { ErrorMessage } from '@/constants/ErrorMessage';
import useLocationState from '@/hooks/useLocationState';
import * as S from '@/pages/VoteDeadlineGenerator/index.styles';
import { afterWeekGenerator, currentTimeGenerator, todayGenerator } from '@/utils/dateGenerator';

const VoteDeadlineGenerator = () => {
	const { articleId, items } = useLocationState<{ articleId: string; items: string[] }>();
	const navigate = useNavigate();
	const { isLoading, mutate, isError, error, isSuccess } = useMutation<
		AxiosResponse<{ articleId: string }>,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>,
		{ articleId: string; items: string[]; expiryDate: string }
	>(registerVoteItems);
	const [deadlineDate, setDeadlineDate] = useState<Record<'date' | 'time', string | undefined>>({
		date: '',
		time: '',
	});
	const [isToday, setIsToday] = useState(false);
	const [isExpireDate, setIsExpireDate] = useState(false);
	const today = todayGenerator();
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
			expiryDate: `${deadlineDate.date}T${deadlineDate.time}`,
		});
	};

	const onChangeDeadlineDate = (e: React.ChangeEvent<HTMLInputElement>) => {
		setDeadlineDate({
			...deadlineDate,
			[e.target.name]: e.target.value,
		});
	};

	useEffect(() => {
		if (deadlineDate.date === today) {
			setIsToday(true);
			return;
		}
		if (deadlineDate.date === afterWeek) {
			setIsExpireDate(true);
			return;
		}
		setIsToday(false);
		setIsExpireDate(false);
	}, [deadlineDate]);

	if (isLoading) return <Loading />;

	return (
		<S.Container onSubmit={handleSubmitVoteDeadlineForm}>
			<S.VoteDeadlineLabel>마감 기한을 설정해주세요.</S.VoteDeadlineLabel>
			<S.VoteDeadlineInputBox>
				<S.DeadlineInput
					type="date"
					name="date"
					required
					min={today}
					max={afterWeek}
					value={deadlineDate.date}
					onChange={onChangeDeadlineDate}
				/>
				<br />
				<S.DeadlineInput
					type="time"
					name="time"
					required
					min={isToday ? currentTimeGenerator() : undefined}
					max={isExpireDate ? '23:59' : undefined}
					value={deadlineDate.time}
					onChange={onChangeDeadlineDate}
				/>
				<S.ValidateMessage>{`투표 마감기간은 ${today}에서 ${afterWeek} 11:59분까지 설정할수 있습니다.`}</S.ValidateMessage>
			</S.VoteDeadlineInputBox>
			<S.SubmitButton>등록하기</S.SubmitButton>
		</S.Container>
	);
};

export default VoteDeadlineGenerator;
