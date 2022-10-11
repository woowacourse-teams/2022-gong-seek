import { AiFillPlusCircle, AiOutlineComment } from 'react-icons/ai';

import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const CommentSection = styled.section`
	display: flex;

	flex-direction: column;
	justify-content: center;
	align-items: center;

	width: 90%;

	${({ theme }) => css`
		margin-top: ${theme.size.SIZE_050};
		padding: 0 ${theme.size.SIZE_010};
	`}
`;

export const CommentInputBox = styled.div`
	display: flex;

	justify-content: space-between;

	width: 100%;

	margin-bottom: ${({ theme }) => theme.size.SIZE_030};
`;

export const CommentInput = styled.div<{ disabled: boolean }>`
	width: 90%;
	border-style: none;

	${({ theme, disabled }) => css`
		border-radius: ${theme.size.SIZE_008};
		background-color: ${theme.colors.GRAY_500};
		padding: ${theme.size.SIZE_008} 0;
		opacity: ${disabled && 0.5};
	`}
`;

export const CommentHeader = styled.div`
	display: flex;

	justify-content: space-between;
	align-items: center;

	width: 100%;

	margin-bottom: ${({ theme }) => theme.size.SIZE_020};
`;

export const CreateCommentButton = styled(AiFillPlusCircle)<{ disabled: boolean }>`
	${({ theme, disabled }) => css`
		width: ${theme.size.SIZE_024};
		height: ${theme.size.SIZE_024};

		color: ${disabled ? `${theme.colors.GRAY_500}` : `${theme.colors.PURPLE_500}`};

		&:hover,
		&:active {
			color: ${!disabled && `${theme.colors.PURPLE_400}`};
		}
	`}
`;

export const CommentTitle = styled.h2`
	font-size: ${({ theme }) => theme.size.SIZE_016};
`;

export const CommentIcon = styled(AiOutlineComment)`
	${({ theme }) => css`
		font-size: ${theme.size.SIZE_024};
		margin-top: -${theme.size.SIZE_006};
	`}
`;

export const CommentTotal = styled.div`
	display: flex;

	gap: ${({ theme }) => theme.size.SIZE_010};

	width: fit-content;
`;

export const DimmerContainer = styled.div`
	position: fixed;

	top: 0;
	left: 0;
	right: 0;
	bottom: 0;

	background-color: ${({ theme }) => theme.colors.GRAY_500};

	z-index: 110;
`;
