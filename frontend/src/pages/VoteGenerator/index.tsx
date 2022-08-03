import React, { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import Input from '@/components/common/Input/Input';
import AddedOption from '@/pages/VoteGenerator/AddedOption/AddedOption';
import * as S from '@/pages/VoteGenerator/index.styles';

const VoteGenerator = () => {
	const [options, setOptions] = useState<string[]>([]);
	const [input, setInput] = useState('');
	const { articleId } = useParams();
	const navigate = useNavigate();

	const onSubmitAddOption = (e: React.FormEvent<HTMLFormElement>) => {
		e.preventDefault();

		setOptions((prevoptions) => prevoptions.concat(input));
		setInput('');
	};

	const onSubmitVoteForm = (e: React.FormEvent<HTMLFormElement>) => {
		e.preventDefault();

		navigate(`/votes-deadline`, { state: { articleId, options } });
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
					<S.AddButtonWrapper>
						<S.AddButton />
					</S.AddButtonWrapper>
				</S.OptionInputBox>
			</S.AddOptionForm>
			<S.ContentForm onSubmit={onSubmitVoteForm}>
				<S.RegisteredOptionTitle>등록된 항목</S.RegisteredOptionTitle>

				<S.Content>
					{options.map((option, idx) => (
						<AddedOption key={idx}>{option}</AddedOption>
					))}
				</S.Content>
				<S.SubmitButton>등록하기</S.SubmitButton>
			</S.ContentForm>
		</S.Container>
	);
};

export default VoteGenerator;
