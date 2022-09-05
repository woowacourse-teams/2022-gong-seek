import React, { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import Input from '@/components/common/Input/Input';
import useSnackBar from '@/hooks/useSnackBar';
import AddedOption from '@/pages/VoteGenerator/AddedOption/AddedOption';
import * as S from '@/pages/VoteGenerator/index.styles';
import {
	validatedDuplicatedVoteItemInput,
	validatedVoteItemInputLength,
	validatedVoteItemsQuantity,
} from '@/utils/validateInput';

const VoteGenerator = () => {
	const [options, setOptions] = useState<string[]>([]);
	const [input, setInput] = useState('');
	const { articleId } = useParams();
	const { showSnackBar } = useSnackBar();
	const navigate = useNavigate();

	const onSubmitAddOption = (e: React.FormEvent<HTMLFormElement>) => {
		e.preventDefault();
		if (input.length === 0) {
			return;
		}
		setOptions((prevoptions) => prevoptions.concat(input));
		setInput('');
	};

	const onSubmitVoteForm = (e: React.FormEvent<HTMLFormElement>) => {
		e.preventDefault();

		if (!validatedVoteItemsQuantity(options)) {
			showSnackBar('투표목록은 최소 2개이상이여야 합니다.');
			return;
		}

		navigate(`/votes-deadline`, { state: { articleId, items: options } });
	};

	const onClickDeleteOptionButton = (id: number) => {
		const filteredOptions = options.filter((_, idx) => idx !== id);
		setOptions(filteredOptions);
	};

	const selectValidateMessage = (input: string, options: string[]) => {
		if (!validatedVoteItemInputLength(input)) {
			return '투표목록은 최소 1글자이상 이여야합니다.';
		}

		if (!validatedDuplicatedVoteItemInput(input, options)) {
			return '중복된 입력이 있습니다.';
		}

		return '유효한 입력입니다.';
	};

	return (
		<S.Container>
			<S.AddOptionForm onSubmit={onSubmitAddOption}>
				<S.OptionInputBox>
					<Input
						width="70%"
						type="text"
						value={input}
						onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
							setInput(e.target.value);
						}}
					/>
					<S.AddButtonWrapper
						disabled={
							!validatedDuplicatedVoteItemInput(input, options) ||
							!validatedVoteItemInputLength(input)
						}
					>
						<S.AddButton />
					</S.AddButtonWrapper>
				</S.OptionInputBox>
			</S.AddOptionForm>
			<S.InputValidMessage
				isValid={
					validatedDuplicatedVoteItemInput(input, options) && validatedVoteItemInputLength(input)
				}
			>
				{selectValidateMessage(input, options)}
			</S.InputValidMessage>

			<S.ContentForm onSubmit={onSubmitVoteForm}>
				<S.RegisteredOptionTitle>등록된 항목</S.RegisteredOptionTitle>

				<S.Content>
					{options.map((option, idx) => (
						<AddedOption key={idx} onClick={() => onClickDeleteOptionButton(idx)}>
							{option}
						</AddedOption>
					))}
				</S.Content>
				<S.SubmitButton>등록하기</S.SubmitButton>
			</S.ContentForm>
		</S.Container>
	);
};

export default VoteGenerator;
