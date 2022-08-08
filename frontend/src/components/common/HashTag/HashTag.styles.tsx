import styled from '@emotion/styled';

export const Container = styled.div``;

export const HastTagItem = styled.div`
	border-radius: ${({ theme }) => theme.size.SIZE_004};
	background-color: ${({ theme }) => theme.colors.PURPLE_400};
`;

export const HashTagForm = styled.form``;

export const HastTagInput = styled.input``;

export const ErrorMessage = styled.div`
	color: ${({ theme }) => theme.colors.RED_500};
`;
