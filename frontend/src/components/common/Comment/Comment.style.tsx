import styled from '@emotion/styled';

export const Container = styled.div`
	display: flex;
	flex-direction: column;
	width: 100%;
	height: fit-content;
	justify-content: space-between;
	gap: ${({ theme }) => theme.size.SIZE_014};
	border-bottom: ${({ theme }) => theme.size.SIZE_001} solid
		${({ theme }) => theme.colors.BLACK_200};
	margin-bottom: ${({ theme }) => theme.size.SIZE_020};
`;

export const CommentHeader = styled.div`
	display: flex;
	width: 100%;
	justify-content: space-between;
	border-bottom: ${({ theme }) => theme.size.SIZE_001} solid
		${({ theme }) => theme.colors.BLACK_200};
`;

export const CommentInfo = styled.div`
	display: flex;
	justify-content: center;
	gap: ${({ theme }) => theme.size.SIZE_004};
`;

export const UserProfile = styled.img`
	width: ${({ theme }) => theme.size.SIZE_050};
	height: ${({ theme }) => theme.size.SIZE_050};
	object-fit: cover;
	object-position: center;
	border-radius: 50%;
`;

export const CommentInfoSub = styled.div`
	display: flex;
	flex-direction: column;
	justify-content: space-around;
`;

export const UserName = styled.p`
	font-size: ${({ theme }) => theme.size.SIZE_016};
`;

export const CreateTime = styled.span`
	font-size: ${({ theme }) => theme.size.SIZE_012};
	color: ${({ theme }) => theme.colors.BLACK_600};
	font-weight: 300;
	opacity: 0.5;
`;

export const CommentAuthBox = styled.div`
	display: flex;
	flex-direction: row;
	justify-content: space-around;
`;

export const Button = styled.button`
	border: none;
	background-color: transparent;
	font-size: ${({ theme }) => theme.size.SIZE_014};
	font-weight: 300;
	color: ${({ theme }) => theme.colors.BLACK_600};

	&:hover,
	&:active {
		color: ${({ theme }) => theme.colors.PURPLE_500};
	}
`;

export const CommentContent = styled.div`
	width: 100%;
	font-size: ${({ theme }) => theme.size.SIZE_014};
	line-height: ${({ theme }) => theme.size.SIZE_030};
	word-break: keep-all;
`;
