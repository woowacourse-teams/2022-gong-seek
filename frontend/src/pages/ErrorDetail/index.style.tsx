import styled from '@emotion/styled';
import { BiCommentDetail } from 'react-icons/bi';

export const Container = styled.article`
	width: 100%;
	align-items: center;
`;

export const CommentSection = styled.section`
	margin-top: ${({ theme }) => theme.size.SIZE_050};
`;

export const CommentHeader = styled.div`
	width: 100%;
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: ${({ theme }) => theme.size.SIZE_020};
`;

export const CommentTitle = styled.h2`
	font-size: ${({ theme }) => theme.size.SIZE_016};
	// margin-left: ${({ theme }) => theme.size.SIZE_010};
`;

export const CommentIcon = styled(BiCommentDetail)``;

export const CommentTotal = styled.div`
	display: flex;
	gap: ${({ theme }) => theme.size.SIZE_014};
`;
