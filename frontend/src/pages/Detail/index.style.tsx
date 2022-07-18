import styled from '@emotion/styled';
import { AiFillPlusCircle } from 'react-icons/ai';
import { BiCommentDetail } from 'react-icons/bi';

export const Container = styled.article`
	width: 100%;
	display: flex;
	flex-direction: column;
	align-items: center;
	align-items: center;
`;

export const CommentSection = styled.section`
	display: flex;
	justify-content: center;
	align-items: center;
	flex-direction: column;
	margin-top: ${({ theme }) => theme.size.SIZE_050};
	padding: 0 ${({ theme }) => theme.size.SIZE_020};
`;

export const CommentInputBox = styled.div`
	display: flex;
	width: 100%;
	justify-content: space-between;
	margin-bottom: ${({ theme }) => theme.size.SIZE_030};
`;

export const CommentInput = styled.div`
	border-style: none;
	padding: 0.5rem 0;
	width: 90%;
	background-color: ${({ theme }) => theme.colors.GRAY_500};
	border-radius: ${({ theme }) => theme.size.SIZE_008};
`;

export const CommentHeader = styled.div`
	width: 100%;
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: ${({ theme }) => theme.size.SIZE_020};
`;

export const CreateCommentButton = styled(AiFillPlusCircle)`
	width: ${({ theme }) => theme.size.SIZE_024};
	height: ${({ theme }) => theme.size.SIZE_024};
	color: ${({ theme }) => theme.colors.PURPLE_500};

	&:hover,
	&:active {
		color: ${({ theme }) => theme.colors.PURPLE_400};
	}
`;

export const CommentTitle = styled.h2`
	font-size: ${({ theme }) => theme.size.SIZE_016};
`;

export const CommentIcon = styled(BiCommentDetail)`
	width: ${({ theme }) => theme.size.SIZE_024};
	height: ${({ theme }) => theme.size.SIZE_024};
`;

export const CommentTotal = styled.div`
	width: fit-content;
	display: flex;
	gap: ${({ theme }) => theme.size.SIZE_010};
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
